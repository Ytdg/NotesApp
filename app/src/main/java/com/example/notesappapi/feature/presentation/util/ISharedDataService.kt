package com.example.notesappapi.feature.presentation.model

import com.example.notesappapi.NavDestinationArgumentsDefaultValue

 interface ISharedDataServiseVm{
    var idNote:Int
}
 class SharedDataVm:ISharedDataServiseVm{
    override var idNote:Int=NavDestinationArgumentsDefaultValue.passId
        get() = field
        set(value){
            field = value
        }
}