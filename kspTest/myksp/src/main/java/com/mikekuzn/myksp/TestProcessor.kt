package com.mikekuzn.myksp


import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

class TestProcessor(
    private val options: Map<String, String>,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val factories = resolver.factories()
        val elements = resolver.elements(factories)
        elements.forEach {
            genFile(it.key, it.value).writeTo(codeGenerator, Dependencies(true))
        }
        return emptyList()
    }

    private fun Resolver.factories() =
        getSymbolsWithAnnotation(AutoFactory::class.qualifiedName.orEmpty())
            .filter()
            .toSet()

    private fun Resolver.elements(
        factories: Set<KSClassDeclaration>
    ): Map<KSClassDeclaration, List<ClassName>> {
        val result = mutableMapOf<KSClassDeclaration, MutableList<ClassName>>()
        factories.forEach { result[it] = mutableListOf() }
        getSymbolsWithAnnotation(AutoElement::class.qualifiedName.orEmpty())
            .filter()
            .forEach { d ->
                d.superTypes
                    .map { it.resolve().declaration.closestClassDeclaration() }
                    .filter { result.containsKey(it) }
                    .forEach { name ->
                        result[name]?.add(d.toClassName())
                    }
            }
        return result
    }

    private fun Sequence<KSAnnotated>.filter() =
        filterIsInstance<KSClassDeclaration>()
            .filter(KSNode::validate)


    private fun genFile(clz: KSClassDeclaration, list: List<ClassName>): FileSpec {
        val key = clz.toClassName()
        val packageName = key.packageName
        val fileName = "Generated${key.simpleName}Factory"
        val funcName = key.simpleName + "Factory"
        val enumName = key.simpleName + "Type"

        return FileSpec.builder(packageName, fileName)

            .addType(TypeSpec.enumBuilder(enumName)
                .apply {
                    list.forEach {
                        addEnumConstant(it.simpleName.uppercase())
                    }
                }
                .build())

            .addFunction(FunSpec.builder(funcName)
                .addParameter("key", ClassName(packageName, enumName))
                .apply {
                    clz.primaryConstructor?.parameters?.forEach {
                        addParameter(
                            it.name?.getShortName().toString(),
                            it.type.resolve().toClassName()
                        )
                    }
                }
                .returns(key)
                .beginControlFlow("return when (key)")
                .apply {
                    val extraParameter =
                        clz.primaryConstructor?.parameters?.map { it.name?.getShortName() }
                            ?.joinToString()
                    list.forEach {
                        addStatement(
                            "${enumName}.${it.simpleName.uppercase()} -> %T($extraParameter)",
                            it
                        )
                    }
                }
                .endControlFlow()
                .build())
            .build()
    }
}