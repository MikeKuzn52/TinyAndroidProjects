package com.mikekuzn.tileserviceexample

import android.content.Intent
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

private const val TAG = "MyTileService"

class MyTileService : TileService() {

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onTileAdded() {
        super.onTileAdded()
        Log.i(TAG, "onTileAdded")
        qsTile.state = Tile.STATE_ACTIVE
        qsTile.icon = Icon.createWithResource(this, R.drawable.off)
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        Log.i(TAG, "onTileRemoved")
    }

    override fun onStartListening() {
        super.onStartListening()
        Log.i(TAG, "onStartListening")
    }

    override fun onStopListening() {
        super.onStopListening()
        Log.i(TAG, "onStopListening")
    }

    override fun onClick() {
        super.onClick()
        Log.i(TAG, "onClick")
        if (qsTile.state == Tile.STATE_ACTIVE) {
            qsTile.state = Tile.STATE_INACTIVE
            qsTile.icon = Icon.createWithResource(this, R.drawable.on)
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        } else{
            qsTile.state = Tile.STATE_ACTIVE
            qsTile.icon = Icon.createWithResource(this, R.drawable.off)
        }
        qsTile.updateTile()
    }

}