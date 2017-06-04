SCRIPT_START
{
    
    LVAR_INT pLabel // In
    LVAR_INT scplayer
    LVAR_INT hCharSec1 hChars[10]
    LVAR_INT hTaskWatch hTaskShake hTaskNod
    LVAR_INT i j iRandom bCancelled iHour iMinute

    // The coords MUST BE the same as in Urbanize.sc
    CONST_FLOAT Queue1_X 1124.9769 
    CONST_FLOAT Queue1_Y -1129.0265
    CONST_FLOAT Queue1_Z 23.8047
    CONST_FLOAT Queue1_R 150.0

    GET_PLAYER_CHAR 0 scplayer

////////////////////////////////////////////// Init ///////////////////////////////////////////////

    // load
    REQUEST_MODEL CELLPHONE
    REQUEST_MODEL WBDYG1
    REQUEST_ANIMATION COP_AMBIENT
    WHILE NOT HAS_MODEL_LOADED CELLPHONE
    OR NOT HAS_MODEL_LOADED WBDYG1
    OR NOT HAS_ANIMATION_LOADED COP_AMBIENT
        WAIT 0
    ENDWHILE

    // Init
    CREATE_CHAR (PEDTYPE_CIVMALE, WBDYG1) (1125.3217 -1127.6753 23.8047) (hCharSec1)
    SET_CHAR_HEADING hCharSec1 126.5
    CLEO_CALL SetAttributes 0 (hCharSec1)

    CREATE_RANDOM_CHAR 1124.9769 -1129.0 23.0 hChars[0]
    CREATE_RANDOM_CHAR 1125.3427 -1129.9 23.0 hChars[1]
    CREATE_RANDOM_CHAR 1126.2548 -1130.5 23.0 hChars[2]
    CREATE_RANDOM_CHAR 1127.2627 -1131.2 23.0 hChars[3]
    CREATE_RANDOM_CHAR 1128.3729 -1131.8 23.0 hChars[4]
    CREATE_RANDOM_CHAR 1131.5227 -1131.8 23.0 hChars[5]
    CREATE_RANDOM_CHAR 1132.5637 -1131.8 23.0 hChars[6]
    CREATE_RANDOM_CHAR 1133.6558 -1131.8 23.0 hChars[7]
    CREATE_RANDOM_CHAR 1134.7734 -1131.8 23.0 hChars[8]
    CREATE_RANDOM_CHAR 1135.8541 -1131.8 23.0 hChars[9]

    REPEAT 10 i
        CLEO_CALL SetAttributes 0 (hChars[i])
        j = i - 1
        TASK_TURN_CHAR_TO_FACE_CHAR hChars[i] hChars[j]
        TASK_FOLLOW_FOOTSTEPS hChars[i] hChars[j]
    ENDREPEAT

    bCancelled = FALSE



    // ---------- Tasks
    // Guard
    TASK_LOOK_ABOUT hCharSec1 -1

    // Watch
    OPEN_SEQUENCE_TASK hTaskWatch
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_in COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_loop COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_watch COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_loop COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_out COP_AMBIENT) (4.0 0 0 0 0 -1)    
    CLOSE_SEQUENCE_TASK hTaskWatch

    // Shake
    OPEN_SEQUENCE_TASK hTaskShake
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_in COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_loop COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_shake COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_out COP_AMBIENT) (4.0 0 0 0 0 -1)    
    CLOSE_SEQUENCE_TASK hTaskShake

    // "Nod"
    OPEN_SEQUENCE_TASK hTaskNod
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_in COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_loop COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_nod COP_AMBIENT) (4.0 0 0 0 0 -1)
    TASK_PLAY_ANIM_NON_INTERRUPTABLE -1 (Coplook_out COP_AMBIENT) (4.0 0 0 0 0 -1)    
    CLOSE_SEQUENCE_TASK hTaskNod

////////////////////////////////////////////// Loop ///////////////////////////////////////////////

    WHILE LOCATE_CHAR_ANY_MEANS_3D scplayer (Queue1_X Queue1_Y Queue1_Z) (Queue1_R Queue1_R Queue1_R) 0
        WAIT 500 // important

        IF bCancelled = FALSE

            // Common actions
            GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)

            IF iRandom = 1
            OR iRandom > 7
                GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)
                j = iRandom - 1 // note: if j = -1, will correctly be hCharSec1
                TASK_TURN_CHAR_TO_FACE_CHAR hChars[iRandom] hChars[j]
            ELSE
                SWITCH iRandom
                    CASE 2
                        GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)
                        TASK_SCRATCH_HEAD hChars[iRandom]
                        BREAK
                    CASE 3
                        GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)
                        TASK_SAY hChars[iRandom] 161
                        BREAK
                    CASE 4
                        GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)
                        TASK_SAY hChars[iRandom] 162
                        BREAK
                ENDSWITCH
            ENDIF

            WAIT 500

            // Rare actions
            GENERATE_RANDOM_INT_IN_RANGE 0 40 (iRandom)

            SWITCH iRandom
                CASE 1
                    GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)
                    TASK_USE_MOBILE_PHONE hChars[iRandom] ON
                    WAIT 4000
                    TASK_USE_MOBILE_PHONE hChars[iRandom] OFF
                    BREAK
                CASE 2
                    GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)
                    PERFORM_SEQUENCE_TASK hChars[iRandom] hTaskWatch
                    BREAK
                CASE 3
                    GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)
                    PERFORM_SEQUENCE_TASK hChars[iRandom] hTaskShake
                    BREAK
                CASE 4
                    GENERATE_RANDOM_INT_IN_RANGE 0 10 (iRandom)
                    PERFORM_SEQUENCE_TASK hChars[iRandom] hTaskNod
                    BREAK
            ENDSWITCH

            GET_TIME_OF_DAY iHour iMinute
            IF iHour >= 20
            OR iHour <= 4
            ELSE
                REPEAT 10 i
                    TASK_WANDER_STANDARD hChars[i]
                    MARK_CHAR_AS_NO_LONGER_NEEDED hChars[i]
                ENDREPEAT
                bCancelled = TRUE
            ENDIF

        ENDIF

    ENDWHILE

///////////////////////////////////////////// Release /////////////////////////////////////////////

    MARK_MODEL_AS_NO_LONGER_NEEDED CELLPHONE
    MARK_MODEL_AS_NO_LONGER_NEEDED WBDYG1
    REMOVE_ANIMATION COP_AMBIENT

    CLEAR_SEQUENCE_TASK hTaskWatch
    CLEAR_SEQUENCE_TASK hTaskShake
    CLEAR_SEQUENCE_TASK hTaskNod

    IF bCancelled = FALSE
        REPEAT 10 i
            REMOVE_CHAR_ELEGANTLY hChars[i]
        ENDREPEAT
    ENDIF

    WRITE_MEMORY pLabel 1 0 FALSE
}
SCRIPT_END

////////////////////////////////////////////// Funcs //////////////////////////////////////////////

{
    LVAR_INT hChar

    SetAttributes:
    TASK_KINDA_STAY_IN_SAME_PLACE hChar ON
    CLEO_RETURN 0
}
