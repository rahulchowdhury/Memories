package co.rahulchowdhury.monet

import kotlinx.coroutines.Job

data class MonetContext(
    var job: Job = Job()
)
