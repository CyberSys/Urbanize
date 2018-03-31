SCRIPT_START
REQUIRE Randomize.sc
REQUIRE GetRandomInt_WithException.sc
REQUIRE RemoveObj.sc
{
    //--- Defintions
    LVAR_INT pLabel // In
    LVAR_INT hObject // In
    LVAR_INT scplayer
    LVAR_INT i j k iRandom iNumChars iEnd iCurrentChar iRandomChar
    LVAR_FLOAT x y z fObjAngle fCharAngle
    LVAR_INT hChars[4] hTasks[4] 
    CONST_FLOAT OBJ_RADIUS 150.0

    //--- Init script
    GET_PLAYER_CHAR 0 scplayer

    //--- Load
    REQUEST_ANIMATION MISC
    WHILE NOT HAS_ANIMATION_LOADED MISC 
        WAIT 0
    ENDWHILE
    
///////////////////////////////////////////////////////////////////////////////////////////////////
    IF DOES_OBJECT_EXIST hObject

        //--- Init CHARs
        GET_OBJECT_HEADING hObject (fObjAngle)
        
        IF CLEO_CALL Randomize 0 (50)
            GET_OFFSET_FROM_OBJECT_IN_WORLD_COORDS hObject (0.05 0.27 0.65) (x y z)
            CREATE_RANDOM_CHAR (x y z) hChars[iNumChars]
            SET_CHAR_COORDINATES_NO_OFFSET hChars[iNumChars] (x y z)
            fCharAngle = fObjAngle + 180.0
            SET_CHAR_HEADING hChars[iNumChars] fCharAngle
            iNumChars++
        ENDIF

        IF CLEO_CALL Randomize 0 (50)
            GET_OFFSET_FROM_OBJECT_IN_WORLD_COORDS hObject (-0.5 0.13 0.65) (x y z)
            CREATE_RANDOM_CHAR (x y z) hChars[iNumChars]
            SET_CHAR_COORDINATES_NO_OFFSET hChars[iNumChars] (x y z)
            fCharAngle = fObjAngle + 180.0
            SET_CHAR_HEADING hChars[iNumChars] fCharAngle
            iNumChars++
        ENDIF

        IF CLEO_CALL Randomize 0 (50)
            GET_OFFSET_FROM_OBJECT_IN_WORLD_COORDS hObject (0.2 -0.96 0.65) (x y z)
            CREATE_RANDOM_CHAR (x y z) hChars[iNumChars]
            SET_CHAR_COORDINATES_NO_OFFSET hChars[iNumChars] (x y z)
            fCharAngle = fObjAngle + 315.0
            SET_CHAR_HEADING hChars[iNumChars] fCharAngle
            iNumChars++
        ENDIF

        IF CLEO_CALL Randomize 0 (50)
            GET_OFFSET_FROM_OBJECT_IN_WORLD_COORDS hObject (0.7 -0.15 0.65) (x y z)
            CREATE_RANDOM_CHAR (x y z) hChars[iNumChars]
            SET_CHAR_COORDINATES_NO_OFFSET hChars[iNumChars] (x y z)
            fCharAngle = fObjAngle + 60.0
            SET_CHAR_HEADING hChars[iNumChars] fCharAngle
            iNumChars++
        ENDIF

        // Set chars attributes
        i = 0
        WHILE i < iNumChars
            SET_CHAR_COLLISION hChars[i] OFF
            FREEZE_CHAR_POSITION hChars[i] ON
            i++
        ENDWHILE

        //--- Init tasks
        IF iNumChars = 1 // single person
            OPEN_SEQUENCE_TASK hTasks[0]
            GENERATE_RANDOM_INT_IN_RANGE 10000 30000 (iRandom)
            TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 SEAT_idle PED (4.0 1 0 0 1) iRandom
            IF CLEO_CALL Randomize 0 (30)
                TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 SEAT_watch MISC (4.0 0 0 0 1) -1
            ENDIF
            SET_SEQUENCE_TO_REPEAT hTasks[0] ON
            CLOSE_SEQUENCE_TASK hTasks[0]
            PERFORM_SEQUENCE_TASK hChars[0] hTasks[0]
        ELSE
            iCurrentChar = 0
            WHILE iCurrentChar < iNumChars
                OPEN_SEQUENCE_TASK hTasks[iCurrentChar]
                REPEAT 6 i
                    GENERATE_RANDOM_INT_IN_RANGE 1 7 (iRandom)
                    SWITCH iRandom

                        CASE 1
                            iEnd = iNumChars + 1
                            CLEO_CALL GetRandomInt_WithException 0 (0 iEnd, iCurrentChar)(iRandomChar)
                            GENERATE_RANDOM_INT_IN_RANGE 4000 8000 (iRandom)
                            TASK_LOOK_AT_CHAR -1 hChars[iRandomChar] iRandom
                            TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 Seat_talk_01 MISC (4.0 0 0 0 1) -1
                            BREAK
                        
                        CASE 2
                            iEnd = iNumChars + 1
                            CLEO_CALL GetRandomInt_WithException 0 (0 iEnd, iCurrentChar)(iRandomChar)
                            GENERATE_RANDOM_INT_IN_RANGE 4000 8000 (iRandom)
                            TASK_LOOK_AT_CHAR -1 hChars[iRandomChar] iRandom
                            TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 Seat_talk_02 MISC (4.0 0 0 0 1) -1
                            BREAK

                        CASE 3
                            TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 SEAT_idle PED (4.0 0 0 0 1) 5000
                            BREAK

                        CASE 4
                            IF CLEO_CALL Randomize 0 (10)
                                TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 SEAT_idle PED (4.0 0 0 0 1) 5000
                                TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 SEAT_watch PED (4.0 0 0 0 1) -1
                            ELSE
                                TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 SEAT_idle PED (4.0 0 0 0 1) 10000
                            ENDIF
                            BREAK

                        DEFAULT
                            TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 SEAT_idle PED (4.0 0 0 0 1) 10000
                            BREAK

                    ENDSWITCH
                ENDREPEAT
                SET_SEQUENCE_TO_REPEAT hTasks[iCurrentChar] ON
                CLOSE_SEQUENCE_TASK hTasks[iCurrentChar]
                PERFORM_SEQUENCE_TASK hChars[iCurrentChar] hTasks[iCurrentChar]
                iCurrentChar++
            ENDWHILE
        ENDIF
///////////////////////////////////////////////////////////////////////////////////////////////////

        main_loop:
        WAIT 0
        IF DOES_OBJECT_EXIST hObject
            IF LOCATE_CHAR_ANY_MEANS_OBJECT_3D scplayer hObject (OBJ_RADIUS OBJ_RADIUS OBJ_RADIUS) 0
                GOTO main_loop
            ENDIF // if not, release:
        ENDIF

        // Release
        i = 0
        WHILE i < iNumChars
            CLEAR_SEQUENCE_TASK hTasks[i]
            REMOVE_CHAR_ELEGANTLY hChars[i]
            i++
        ENDWHILE

    ENDIF // object not exist

    REMOVE_ANIMATION MISC
    CLEO_CALL Remove_Object 0 (pLabel hObject)
}
SCRIPT_END
