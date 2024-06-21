package com.example.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchViewModel : ViewModel() {
    private var _time = 0L
    val time: Long get() = _time

    private var _isRunning = false
    val isRunning: Boolean get() = _isRunning

    fun start() {
        if (!_isRunning) {
            _isRunning = true
            viewModelScope.launch {
                while (_isRunning) {
                    delay(1000L)
                    _time += 1L
                }
            }
        }
    }

    fun stop() {
        _isRunning = false
    }

    fun reset() {
        _isRunning = false
        _time = 0L
    }
}
