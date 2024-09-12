package common.domain

sealed interface DataState {

    @JvmInline
    value class Data(val value: String) : DataState, CharSequence by value

    data object EMPTY : DataState

    data object LOADING : DataState
}
