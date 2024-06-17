package com.mikekuzn.workertest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mikekuzn.workertest.ui.theme.WorkerTestTheme
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

const val WORKER_TAG: String = "ANY_TAG"
const val NUM_TASK: String = "NUM_TASK"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject @Named("Name of my any param")
    lateinit var anyString: String

    private var id: UUID? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkerTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        Log.i("TestWorker", "MainActivity anyString=$anyString")
    }

    @Composable
    fun Greeting(modifier: Modifier) {
        val context = LocalContext.current
        Column(
            modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(text = "This is a test application. The test results are shown in the log with the tag \"TestWorker\"")
            Button(
                onClick = { testOneTimeWorker(context) },
                Modifier.fillMaxWidth(),
            ) {
                Text(text = "Test OneTime Worker")
            }
            Button(
                onClick = { testOneTimeWorkerWithRequires(context) },
                Modifier.fillMaxWidth(),
            ) {
                Text(text = "Test OneTime Worker with Requires")
            }
            Button(
                onClick = { testOneTimeWorkerBySequence(context) },
                Modifier.fillMaxWidth(),
            ) {
                Text(text = "Test OneTime Workers by Sequence")
            }
            Button(
                onClick = { testOneTimeWorkerByParallels(context) },
                Modifier.fillMaxWidth(),
            ) {
                Text(text = "Test OneTime Workers by Parallels")
            }
            Button(
                onClick = { stopByTag(context) },
                Modifier.fillMaxWidth(),
            ) {
                Text(text = "Stop all Workers by Tag")
            }
            Button(
                onClick = { getRequestStatus(context) },
                Modifier.fillMaxWidth(),
            ) {
                Text(text = "Get Status of Request")
            }
        }
    }

    private fun testOneTimeWorker(context: Context) {
        Log.i("TestWorker", "TestOneTimeWorker app=${applicationInfo}")
        val myWorkRequest = OneTimeWorkRequestBuilder<Worker1>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .addTag(WORKER_TAG)
            .build()
        id = myWorkRequest.id
        WorkManager.getInstance(context).enqueue(myWorkRequest)
    }

    private fun testOneTimeWorkerWithRequires(context: Context) {
        Log.i("TestWorker", "TestOneTimeWorkerWithRequires app=${applicationInfo}")
        val constraints = Constraints.Builder()
            .setRequiresCharging(true) // критерий: зарядное устройство должно быть подключено.
            .setRequiresBatteryNotLow(true) // критерий: уровень батареи не ниже критического (задача начинает выполняться при уровне заряда больше 20, а останавливается при значении меньше 16).
            .setRequiredNetworkType(NetworkType.UNMETERED) // критерий: наличие интернета. CONNECTED — WiFi или Mobile Data UNMETERED — только WiFi ...
            //.setRequiresDeviceIdle(true) // критерий: девайс не используется какое-то время и ушел “в спячку”
            //.setRequiresStorageNotLow(true) // критерий: на девайсе должно быть свободное место, не меньше критического порога.
            .build()
        val myWorkRequest = OneTimeWorkRequestBuilder<Worker1>()
            .setInitialDelay(2, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .addTag(WORKER_TAG)
            .build()
        id = myWorkRequest.id
        WorkManager.getInstance(context).enqueue(myWorkRequest)
    }

    private val myData = Data.Builder()
        .putString("keyA", "value1")
        .putInt("keyB", 1)

    private val simpleRequests = listOf(
        OneTimeWorkRequestBuilder<Worker2>()
            .setInputData(
                myData
                    .putInt(NUM_TASK, 1)
                    .build()
            )
            .build(),
        OneTimeWorkRequestBuilder<Worker2>()
            .setInputData(
                myData
                    .putInt(NUM_TASK, 2)
                    .build()
            )
            .build(),
        OneTimeWorkRequestBuilder<Worker2>()
            .setInputData(
                myData
                    .putInt(NUM_TASK, 3)
                    .build()
            )
            .build(),
        OneTimeWorkRequestBuilder<Worker2>()
            .setInputData(
                myData
                    .putInt(NUM_TASK, 4)
                    .build()
            )
            //.setInputMerger(ArrayCreatingInputMerger::class.java)
            .build(),
        OneTimeWorkRequestBuilder<Worker2>()
            .setInputData(
                myData
                    .putInt(NUM_TASK, 5)
                    .build()
            )
            .build(),
        // Периодические задачи ставить в цепочку нельзя.
    )

    private fun testOneTimeWorkerBySequence(context: Context) {
        val myWorkRequest = OneTimeWorkRequestBuilder<Worker3>()
            .build()
        id = myWorkRequest.id
        WorkManager.getInstance(context)
            .beginWith(simpleRequests[0])
            .then(listOf(simpleRequests[1], simpleRequests[2]))
            .then(myWorkRequest)
            .enqueue()
    }

    private fun testOneTimeWorkerByParallels(context: Context) {
        WorkManager.getInstance(context)
            .enqueue(simpleRequests)
    }

    // There are also:
    // WorkContinuation.combine
    // .beginUniqueWork(REPLACE or KEEP or APPEND)

    private fun stopByTag(context: Context) {
        WorkManager.getInstance(context)
            .cancelAllWorkByTag(WORKER_TAG)
        // There are also:
        // cancelAllWork() - not recommended
        // cancelUniqueWork(uniqueWorkName)
        // cancelWorkById(simpleRequests[3].id)
    }
    private fun getRequestStatus(context: Context) {
        id?.let {
            WorkManager.getInstance(context)
                .getWorkInfoByIdLiveData(it)
                .observe(this) { info ->
                    Log.d("TestWorker", "onChanged: " + info.state)
                    info.outputData.keyValueMap.forEach{entry ->
                        Log.i("TestWorker", "observe Key=${entry.key} val=${entry.value}")
                    }
                }
        }
    }

}

@HiltWorker
class Worker1 @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
) : Worker(context, params) {

    @Inject @Named("Name of my any param")
    lateinit var anyString: String

    override fun doWork(): Result {
        Log.i("TestWorker", "Worker1 start anyString=$anyString app=${context.applicationInfo}")
        Thread.sleep(1000)
        Log.i("TestWorker", "Worker1 end")
        return Result.success()
    }
}

class Worker2(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val num = inputData.getInt(NUM_TASK, -1)
        Log.i("TestWorker", "Worker2_$num start")
        inputData.keyValueMap.forEach{
            Log.i("TestWorker", "Worker2_$num  Key=${it.key} val=${it.value}")
        }
        Thread.sleep(1000)
        Log.i("TestWorker", "Worker2_$num end")
        val output = Data.Builder()
            .putString("key$num", "Task$num ready")
            .build()
        return Result.success(output)
    }
}

class Worker3(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.i("TestWorker", "Worker3 start")
        inputData.keyValueMap.forEach{
            Log.i("TestWorker", "Worker3  Key=${it.key} val=${it.value}")
        }
        Thread.sleep(1000)
        Log.i("TestWorker", "Worker3 end")
        return Result.success()
    }
}
