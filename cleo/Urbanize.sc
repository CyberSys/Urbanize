SCRIPT_START
REQUIRE CheckStore.sc
{
LVAR_INT scplayer
LVAR_INT iModel hObject pLabel bActive iHour iMinute
LVAR_FLOAT x y z
CONST_FLOAT OBJ_RADIUS 150.0

GET_PLAYER_CHAR 0 scplayer
 
///////////////////////////////////////////////////////////////////////////////////////////////////

main_loop:
WAIT 0
IF IS_PLAYER_PLAYING 0

    //---------- Triggers

    //-- Locations
    IF GOSUB CheckStart_House1
        STREAM_CUSTOM_SCRIPT "Urbanize/House1.cs" (pLabel) // - EXAMPLE
    ENDIF
    IF GOSUB CheckStart_House2
        STREAM_CUSTOM_SCRIPT "Urbanize/House2.cs" (pLabel) // - EXAMPLE
    ENDIF
    IF GOSUB CheckStart_Queue1
        STREAM_CUSTOM_SCRIPT "Urbanize/Queue1.cs" (pLabel)
    ENDIF

 
    //-- Objects
    GET_CHAR_COORDINATES scplayer (x y z)

    IF GET_RANDOM_OBJECT_IN_SPHERE_NO_SAVE_RECURSIVE (x y z) (OBJ_RADIUS) (FALSE) hObject
        for_all_objects_loop:
        GET_OBJECT_MODEL  hObject iModel 
        /////////////////////////////////////////

        IF iModel = 643 //kb_chr_tbl_test (mesa 4 cadeiras)
            GET_LABEL_POINTER RunningList_Table1 (pLabel)
            IF CLEO_CALL CheckStore_Object 0 (pLabel, hObject)
                STREAM_CUSTOM_SCRIPT "Urbanize/Table1.cs" (pLabel, hObject)
            ENDIF
        ENDIF

        IF iModel = 1415 //dyn_dumpster (entulhos) - TODO
            GET_LABEL_POINTER RunningList_Dump1 (pLabel)
            IF CLEO_CALL CheckStore_Object 0 (pLabel, hObject)
                STREAM_CUSTOM_SCRIPT "Urbanize/Dump1.cs" (pLabel, hObject)
            ENDIF
        ENDIF

        /////////////////////////////////////////
        IF GET_RANDOM_OBJECT_IN_SPHERE_NO_SAVE_RECURSIVE (x y z) (OBJ_RADIUS) (TRUE) hObject
            GOTO for_all_objects_loop
        ENDIF
    ENDIF

    //---------- End of Triggers

ENDIF
GOTO main_loop 

///////////////////////////////////////////////////////////////////////////////////////////////////


//------- House 1 -------//
CheckStart_House1:
CONST_FLOAT House1_X 0.0 
CONST_FLOAT House1_Y 0.0
CONST_FLOAT House1_Z 0.0
CONST_FLOAT House1_R 150.0
IF LOCATE_CHAR_ANY_MEANS_3D scplayer (House1_X House1_Y House1_Z) (House1_R House1_R House1_R) 0
    GET_LABEL_POINTER Running_House1 (pLabel)
    READ_MEMORY pLabel 1 FALSE (bActive)
    IF bActive = FALSE
        WRITE_MEMORY pLabel 4 1 FALSE
        IS_PC_VERSION
        RETURN
    ENDIF
ENDIF
IS_AUSTRALIAN_GAME
RETURN

Running_House1:
DUMP
00
ENDDUMP


//------- House 2 -------//
CheckStart_House2:
CONST_FLOAT House2_X 0.0 
CONST_FLOAT House2_Y 0.0
CONST_FLOAT House2_Z 0.0
CONST_FLOAT House2_R 150.0
IF LOCATE_CHAR_ANY_MEANS_3D scplayer (House2_X House2_Y House2_Z) (House2_R House2_R House2_R) 0
    GET_LABEL_POINTER Running_House2 (pLabel)
    READ_MEMORY pLabel 1 FALSE (bActive)
    IF bActive = FALSE
        WRITE_MEMORY pLabel 4 1 FALSE
        IS_PC_VERSION
        RETURN
    ENDIF
ENDIF
IS_AUSTRALIAN_GAME
RETURN

Running_House2:
DUMP
00
ENDDUMP


//------- Queue 1 -------//
CheckStart_Queue1:
CONST_FLOAT Queue1_X 1124.9769 
CONST_FLOAT Queue1_Y -1129.0265
CONST_FLOAT Queue1_Z 23.8047
CONST_FLOAT Queue1_R 150.0
GET_TIME_OF_DAY iHour iMinute
IF iHour >= 20
OR iHour <= 4
    IF LOCATE_CHAR_ANY_MEANS_3D scplayer (QUEUE1_X QUEUE1_Y QUEUE1_Z) (QUEUE1_R QUEUE1_R QUEUE1_R) 0
        GET_LABEL_POINTER Running_Queue1 (pLabel)
        READ_MEMORY pLabel 1 FALSE (bActive)
        IF bActive = FALSE
            WRITE_MEMORY pLabel 4 1 FALSE
            IS_PC_VERSION
            RETURN
        ENDIF
    ENDIF
ENDIF
IS_AUSTRALIAN_GAME
RETURN

Running_Queue1:
DUMP
00
ENDDUMP

///////////////////////////////////////////////////////////////////////////////////////////////////

RunningList_Table1: // 16 slots
DUMP
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
FF FF FF FF // end
ENDDUMP

RunningList_Dump1: // 16 slots
DUMP
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
FF FF FF FF // end
ENDDUMP

///////////////////////////////////////////////////////////////////////////////////////////////////


}
SCRIPT_END
