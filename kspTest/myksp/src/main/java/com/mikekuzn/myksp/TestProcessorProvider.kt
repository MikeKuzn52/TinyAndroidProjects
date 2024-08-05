package com.mikekuzn.myksp

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class TestProcessorProvider: SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) = TestProcessor(
        options = environment.options,
        logger = environment.logger,
        codeGenerator = environment.codeGenerator
    )
}