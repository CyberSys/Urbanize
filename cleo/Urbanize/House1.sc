/*
    TEMPLATE HOUSE1
Example of creating simple peds doing tasks.
    Complexibility: 2/5
    TESTED: YES
*/
SCRIPT_START
{
    LVAR_INT pLabel // In
    LVAR_INT scplayer
    LVAR_INT hChar1 hChar2
    LVAR_INT hTask1 hTask2
    LVAR_INT iHealth iLastChar1Health iLastChar2Health

    // The coords MUST BE the same as in Urbanize.sc
    CONST_FLOAT House1_X 0.0 
    CONST_FLOAT House1_Y 0.0
    CONST_FLOAT House1_Z 0.0
    CONST_FLOAT House1_R 150.0

    GET_PLAYER_CHAR 0 scplayer

////////////////////////////////////////////// Init ///////////////////////////////////////////////

    // load
    REQUEST_MODEL 101
    REQUEST_MODEL 102
    REQUEST_ANIMATION IFP
    WHILE NOT HAS_MODEL_LOADED 101
    OR NOT HAS_MODEL_LOADED 102
    OR NOT HAS_ANIMATION_LOADED IFP
        WAIT 0
    ENDWHILE

    // Init
    CREATE_CHAR (PEDTYPE_CIVMALE, 101) (0.0 0.0 0.0) (hChar1)
    CLEO_CALL SetAttributes 0 (hChar1)
    CREATE_CHAR (PEDTYPE_CIVMALE, 102) (0.0 0.0 0.0) (hChar2)
    CLEO_CALL SetAttributes 0 (hChar2)

    // Release
    MARK_MODEL_AS_NO_LONGER_NEEDED 101
    MARK_MODEL_AS_NO_LONGER_NEEDED 102

    // ---------- Tasks
    // Task 1
    OPEN_SEQUENCE_TASK hTask1
    TASK_CHAR_SLIDE_TO_COORD_AND_PLAY_ANIM -1 (10.0 10.0 0.0) (0.0) (0.2) (ANIM1 IFP) (4.0 0 0 0 0 -1)
    TASK_CHAR_SLIDE_TO_COORD_AND_PLAY_ANIM -1 (20.0 20.0 0.0) (0.0) (0.2) (ANIM2 IFP) (4.0 0 0 0 0 -1)
    SET_SEQUENCE_TO_REPEAT hTask1 ON
    CLOSE_SEQUENCE_TASK hTask1
    PERFORM_SEQUENCE_TASK hChar1 hTask1

    // Task 1
    OPEN_SEQUENCE_TASK hTask2
    TASK_CHAR_SLIDE_TO_COORD_AND_PLAY_ANIM -1 (20.0 20.0 0.0) (0.0) (0.2) (ANIM2 IFP) (4.0 0 0 0 0 -1)
    TASK_CHAR_SLIDE_TO_COORD_AND_PLAY_ANIM -1 (10.0 10.0 0.0) (0.0) (0.2) (ANIM1 IFP) (4.0 0 0 0 0 -1)
    SET_SEQUENCE_TO_REPEAT hTask1 ON
    CLOSE_SEQUENCE_TASK hTask2
    PERFORM_SEQUENCE_TASK hChar2 hTask2

////////////////////////////////////////////// Loop ///////////////////////////////////////////////

    WHILE LOCATE_CHAR_ANY_MEANS_3D scplayer (House1_X House1_Y House1_Z) (House1_R House1_R House1_R) 0
        WAIT 0

        // Check if Char1 was damaged
        IF DOES_CHAR_EXIST hChar1
            GET_CHAR_HEALTH hChar1 (iHealth)
            IF iHealth < iLastChar1Health
                MARK_CHAR_AS_NO_LONGER_NEEDED hChar1
            ENDIF
            iLastChar1Health = iHealth
        ENDIF

        // Check if Char2 was damaged
        IF DOES_CHAR_EXIST hChar2
            GET_CHAR_HEALTH hChar2 (iHealth)
            IF iHealth < iLastChar2Health
                MARK_CHAR_AS_NO_LONGER_NEEDED hChar2
            ENDIF
            iLastChar2Health = iHealth
        ENDIF

    ENDWHILE

///////////////////////////////////////////// Release /////////////////////////////////////////////

    REMOVE_ANIMATION IFP

    CLEAR_SEQUENCE_TASK hTask1
    CLEAR_SEQUENCE_TASK hTask2

    REMOVE_CHAR_ELEGANTLY hChar1
    REMOVE_CHAR_ELEGANTLY hChar2

    WRITE_MEMORY pLabel 1 0 FALSE
}
SCRIPT_END

////////////////////////////////////////////// Funcs //////////////////////////////////////////////

{
    LVAR_INT hChar

    SetAttributes:
    TASK_STAY_IN_SAME_PLACE hChar ON
    CLEO_RETURN 0
}
