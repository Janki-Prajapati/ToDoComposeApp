package com.jp.test.todocomposeapp.generic.useCase

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}