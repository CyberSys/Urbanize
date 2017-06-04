/*
    TEMPLATE HOUSE2
Example of creating simple peds doing some anim.
    Complexibility: 1/5
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
    CONST_FLOAT House2_X 0.0 
    CONST_FLOAT House2_Y 0.0
    CONST_FLOAT House2_Z 0.0
    CONST_FLOAT House2_R 150.0

    GET_PLAYER_CHAR 0 scplayer

////////////////////////////////////////////// Init ///////////////////////////////////////////////

    // load
    REQUEST_ANIMATION IFP
    WHILE NOT HAS_ANIMATION_LOADED IFP
        WAIT 0
    ENDWHILE

    // Init
    CREATE_RANDOM_CHAR (0.0 0.0 0.0) (hChar1)
    CLEO_CALL SetAttributes 0 (hChar1)
    CREATE_RANDOM_CHAR (0.0 0.0 0.0) (hChar2)
    CLEO_CALL SetAttributes 0 (hChar2)

    // ---------- Tasks
    // Char 1
    TASK_PLAY_ANIM_NON_INTERRUPTABLE hChar1 (ANIM1 IFP) (4.0 1 0 0 0 -1)

    // Char 2
    TASK_PLAY_ANIM_NON_INTERRUPTABLE hChar1 (ANIM2 IFP) (4.0 1 0 0 0 -1)

////////////////////////////////////////////// Loop ///////////////////////////////////////////////

    WHILE LOCATE_CHAR_ANY_MEANS_3D scplayer (House2_X House2_Y House2_Z) (House2_R House2_R House2_R) 0
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
