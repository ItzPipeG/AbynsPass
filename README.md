# AbynsPass

abynspass.admin

 - pass panel - Abrir el panel en el que aparecen todas las misiones.
 - pass start <id> <player/all> - Iniciar una misión para un jugador en especifico, o todos. El grupo tiene que coincidir con el de la config.yml.
 - pass stop <id> - Detener una misión.

%abynspass_mission_actual%
%abynspass_mission_completed%
%abynspass_mission_bar%


TIPOS DE MISION:

- BLOCK PLACE:

    TYPE: 'BLOCK_PLACE' # MOB_KILL, BLOCK_PLACE , BLOCK_BREAK , REGION_JOIN , REGION_LEFT, ITEM_CRAFT, ITEMS_CRAFT                                                                                                                      
    GROUP: GENERAL # GENERAL, PLAYER                                                                                                                      
    DISPLAY_NAME: '&9&lColoca bloques'                                                                                                                      
    VALUE: 'DIRT' # BLOCK                                                                                                                      
    AMOUNT: 5                                                                                                                      
    STARTED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Iniciado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'bc Iniciado!'                                                                                                                      
    COMPLETED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Completado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {player} diamond 1'                                                                                                                      
    FINISHED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Finalizado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {players} emerald 1'                                                                                                                      
                                                                                                                      
- BLOCK BREAK:                                                                                                                      
                                                                                                                      
    TYPE: 'BLOCK_BREAK' # MOB_KILL, BLOCK_PLACE , BLOCK_BREAK , REGION_JOIN , REGION_LEFT, ITEM_CRAFT, ITEMS_CRAFT                                                                                                                      
    GROUP: GENERAL # GENERAL, PLAYER                                                                                                                      
    DISPLAY_NAME: '&9&lRompe bloques'                                                                                                                      
    VALUE: 'DIRT' # BLOCK                                                                                                                      
    AMOUNT: 10                                                                                                                      
    STARTED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Iniciado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'bc Iniciado!'                                                                                                                      
    COMPLETED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Completado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {player} diamond 1'                                                                                                                      
    FINISHED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Finalizado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {players} emerald 1'                                                                                                                      
                                                                                                                      
- MOB KILL:                                                                                                                      
                                                                                                                      
    TYPE: 'MOB_KILL' # MOB_KILL, BLOCK_PLACE , BLOCK_BREAK , REGION_JOIN , REGION_LEFT, ITEM_CRAFT, ITEMS_CRAFT                                                                                                                      
    GROUP: GENERAL # GENERAL, PLAYER                                                                                                                      
    DISPLAY_NAME: '&9&lMatador'                                                                                                                      
    VALUE: 'ZOMBIE' # MOB TYPE                                                                                                                      
    AMOUNT: 5                                                                                                                      
    STARTED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Iniciado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'bc Iniciado!'                                                                                                                      
    COMPLETED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Completado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {player} diamond 1'                                                                                                                      
    FINISHED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Finalizado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {players} emerald 1'                                                                                                                      
                                                                                                                      
- BOSS KILL:                                                                                                                      
                                                                                                                      
    TYPE: 'BOSS_KILL' # MOB_KILL, BLOCK_PLACE , BLOCK_BREAK , REGION_JOIN , REGION_LEFT, ITEM_CRAFT, ITEMS_CRAFT                                                                                                                      
    GROUP: GENERAL # GENERAL, PLAYER                                                                                                                      
    DISPLAY_NAME: '&9&lMatador'                                                                                                                      
    VALUE: '&3Guardian Ahogado' # MOB NAME                                                                                                                      
    AMOUNT: 1                                                                                                                      
    STARTED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Iniciado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'bc Iniciado!'                                                                                                                      
    COMPLETED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Completado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {player} diamond 1'                                                                                                                      
    FINISHED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Finalizado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {players} emerald 1'                                                                                                                      
                                                                                                                      
- REGION JOIN                                                                                                                      
                                                                                                                      
    TYPE: 'REGION_JOIN' # MOB_KILL, BLOCK_PLACE , BLOCK_BREAK , REGION_JOIN , REGION_LEFT, ITEM_CRAFT, ITEMS_CRAFT                                                                                                                      
    GROUP: GENERAL # GENERAL, PLAYER                                                                                                                      
    DISPLAY_NAME: '&9&lEntrador de regiones'                                                                                                                      
    VALUE: 'zonaCombate' # REGION NAME                                                                                                                      
    AMOUNT: 1                                                                                                                      
    STARTED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Iniciado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'bc Iniciado!'                                                                                                                      
    COMPLETED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Completado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {player} diamond 1'                                                                                                                      
    FINISHED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Finalizado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {players} emerald 1'                                                                                                                      
                                                                                                                      
  - REGION LEFT                                                                                                                      
                                                                                                                       
    TYPE: 'REGION_LEFT' # MOB_KILL, BLOCK_PLACE , BLOCK_BREAK , REGION_JOIN , REGION_LEFT, ITEM_CRAFT, ITEMS_CRAFT                                                                                                                      
    GROUP: GENERAL # GENERAL, PLAYER                                                                                                                      
    DISPLAY_NAME: '&9&lSalidor de regiones'                                                                                                                      
    VALUE: 'spawn' # REGION NAME                                                                                                                      
    AMOUNT: 1                                                                                                                      
    STARTED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Iniciado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'bc Iniciado!'                                                                                                                      
    COMPLETED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Completado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {player} diamond 1'                                                                                                                      
    FINISHED:                                                                                                                      
      SOUND: "ENTITY_WITHER_SPAWN"                                                                                                                      
      MESSAGE: "Finalizado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {players} emerald 1'                                                                                                                      
                                                                                                                      
  - ITEMS CRAFT                                                                                                                      
                                                                                                                      
    TYPE: 'ITEMS_CRAFT' # MOB_KILL, BLOCK_PLACE , BLOCK_BREAK , REGION_JOIN , REGION_LEFT, ITEM_CRAFT, ITEMS_CRAFT                                                                                                                      
    GROUP: GENERAL                                                                                                                      
    DISPLAY_NAME: '&9&lCraftero'                                                                                                                      
    VALUE: 'DIAMOND_BOOTS;DIAMOND_LEGGINGS;DIAMOND_CHESTPLATE;DIAMOND_HELMET' # ITEMS                                                                                                                      
    AMOUNT: 1                                                                                                                      
    STARTED:                                                                                                                      
      MESSAGE: "Iniciado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'bc Iniciado!'                                                                                                                      
    COMPLETED:                                                                                                                      
      MESSAGE: "Completado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {player} diamond 1'                                                                                                                      
    FINISHED:                                                                                                                      
      MESSAGE: "Finalizado!"                                                                                                                      
      COMMANDS:                                                                                                                      
        - 'give {players} emerald 1'                                                                                                                      
