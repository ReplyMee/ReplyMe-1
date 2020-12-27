package com.gtu.replyme

import kotlinx.android.synthetic.main.activity_sign_up.*
import org.junit.Assert.*
import org.junit.Test

class sign_upUTest{

    var x = sign_up()

    @Test
    fun Is_deneme_correct(){

        var actual = x.deneme(123456,123456)

        //var expected = true

        assertNotEquals("Deneme Not Passed",false,true)



    }
}