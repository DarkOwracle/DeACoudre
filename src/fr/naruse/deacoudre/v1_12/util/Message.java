package fr.naruse.deacoudre.v1_12.util;

import fr.naruse.deacoudre.main.DacPlugin;
import fr.naruse.deacoudre.manager.DacPluginV1_12;

public enum Message {

    THERE_ARE_NO_PLAYERS("Il n'y a aucuns joueurs dans cette arène.", "There are no players in this arena.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("thereAreNoPlayers"), "thereAreNoPlayers"),
    ARENA_STOPPED("Arène stoppée.", "Arena stopped.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arenaStopped"), "arenaStopped"),
    ARENA_ALREADY_STARTED("L'arène est déjà lancée.", "This arena is already started.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arenaAlreadyStarted"), "arenaAlreadyStarted"),
    ARENA_STARTED("Arène lancée.", "Arena started.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arenaStarted"), "arenaStarted"),
    DONE("Processus terminé.", "Process completed.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("done"), "done"),
    NEEDS_WE("Cette commande nécessite WorldEdit pour fonctionner.", "This command needs WorldEdit to work.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("needsWE"), "needsWE"),
    DAC_IN_OPERATION("Les DACs en fonctions:", "DACs in operation:", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("dacInOperation"), "dacInOperation"),
    DAC_IN_FAILURE("Les DACs mal configurés:", "DACs in incorrectly configured:", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("dacInFailure"), "dacInFailure"),
    REGION_SAVED("Région enregistrée.", "Region saved.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("regionSaved"), "regionSaved"),
    REGION_REMOVED("Région supprimée.", "Region removed.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("regionRemoved"), "regionRemoved"),
    PAGE_NOT_FOUND("Page introuvable.", "Page not found.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("pageNotFound"), "pageNotFound"),
    TOO_MUCH_ARENAS("Il y a trop d'arènes, vous devez en supprimer.", "There are too many arenas, you have to remove them.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("tooMuchArenas"), "tooMuchArenas"),
    NAME_ALREADY_USED("Ce nom d'arène est déjà pris.", "This name is already used.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("nameAlreadyUsed"), "nameAlreadyUsed"),
    GAME_AREADY_CLOSED("Cette partie est déjà fermée.", "This game is already closed.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("gameAlreadyClosed"), "gameAlreadyClosed"),
    ARENA_OPENED("Arène ouverte.", "Arena opened.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arenaOpened"), "arenaOpened"),
    ARENA_CLOSED("Arène fermée.", "Arena closed.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arenaClosed"), "arenaClosed"),
    GAME_ALREADY_OPEN("Cette partie est déjà ouverte.", "This game is already open.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("gameAlreadyOpen"), "gameAlreadyOpen"),
    ARENA_NOT_FOUND("Arène introuvable.", "Arena not found.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arenaNotFound"), "arenaNotFound"),
    GAME_START("La partie commence!", "The game begins!", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("gameStart"), "gameStart"),
    ARENA_CREATED("Arène créée.", "Arena created.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arenaCreated"), "arenaCreated"),
    ARENA_DELETED("Arène supprimée.", "Arena deleted.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arenaDeleted"), "arenaDeleted"),
    LOCATION_SAVED("Location enregistrée.", "Location saved.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("locationSaved"), "locationSaved"),
    TIME_SAVED("Temps enregistré.", "Time saved.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("timeSaved"), "timeSaved"),
    NUMBER_SAVED("Nombre enregistré.", "Number saved.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("numberSaved"), "numberSaved"),
    GREATED_THAN_0("Il faut un nombre supérieur à 0.", "A number greater than 0 is required.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("greaterThan0"), "greaterThan0"),
    NEED_A_NUMBER("Il faut un nombre.", "The plugin needs a number.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("needANumber"), "needANumber"),
    YOU_DONT_HAVE_PERMISSION("Vous n'avez pas la permission.", "You do not have this permission.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("youDontHaveThisPermission"), "youDontHaveThisPermission"),
    NOT_ENOUGH_PLAYER("Il n'y a pas assez de joueurs pour débuter la partie.", "There are not enough players to start the game.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("notEnoughPlayers"), "notEnoughPlayers"),
    WINS_THE_GAME_WITH("remporte la partie avec", "wins the game with", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("winsTheGameWith"), "winsTheGameWith"),
    THEY_WINS_THE_GAME_WITH("remportent la partie avec", "win the game with", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("theyWinTheGameWith"), "theyWinTheGameWith"),
    MADE_PERFECT("a fait un dé à coudre et a gagné une vie!", "made a dé à coudre and win a earned a life!", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("madeDAC"), "madeDAC"),
    SUCCESFULL_JUMP("Saut réussi !", "Successful jump!", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("successfulJump"), "successfulJump"),
    NEXT_TO_JUMP("Vous êtes le prochain à sauter.", "You are next to jump.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("nextToJump"), "nextToJump"),
    IN_GAME("Cette partie est en cours.", "This game is in progress.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("inGame"), "inGame"),
    IN_GAME2("En cours", "In game", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("inGame2"), "inGame2"),
    FULL_GAME("Cette partie est pleine.", "This game is full.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("gameFull"), "gameFull"),
    LEAVE_THIS_GAME("Quitter la partie.", "Leave this game.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("leaveThisGame"), "leaveThisGame"),
    JOINED_THE_GAME("a rejoint la partie.", "joined the game.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("joinedTheGame"), "joinedTheGame"),
    LIVES("vies", "lives", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("lives"), "lives"),
    CLOSED("Fermé", "Closed", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("closed"), "closed"),
    READY("Prêt", "Ready", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("ready"), "ready"),
    MISSING("manquants", "missing", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("missing"), "missing"),
    JOIN("Rejoignez", "Join", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("join"), "join"),
    FAILED("a échoué!", "failed!", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("failed"), "failed"),
    LOST_LIFE("a perdu une vie!", "lost a life!", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("lostLife"), "lostLife"),
    LEAVED("a quitté la partie.", "left the game.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("leaved"), "leaved"),
    YOU_HAVE_A_GAME("Vous êtes déjà dans une partie.", "You are already in game.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("youHaveAGame"), "youHaveAGame"),
    WITHOUT_WORLD("L'arène ** fonctionne sur un monde introuvable.", "The ** arena operates on an untraceable world.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("withoutWorld"), "withoutWorld"),
    WITHOUT_LIMIT("L'arène ** ne possede aucune limite de joueurs.", "The ** arena has no player limit.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("withoutLimit"), "withoutLimit"),
    WITHOUT_NEED("L'arène ** ne possede aucun nombre de joueurs requis.", "The ** arena has no players required.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("withoutNeed"), "withoutNeed"),
    WITHOUT_POOL("L'arène ** ne possede aucune location \"pool\".", "The ** arena has no 'pool' location.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("withoutPool"), "withoutPool"),
    WITHOUT_END("L'arène ** ne possede aucune location \"end\".", "The ** arena has no 'end' location.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("withoutEnd"), "withoutEnd"),
    WITHOUT_LOBBY("L'arène ** ne possede aucune location \"lobby\".", "The ** arena has no 'lobby' location.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("withoutLobby"), "withoutLobby"),
    WITHOUT_DIVING("L'arène ** ne possede aucun plongeoir.", "The arena ** has no 'diving' board.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("withoutDiving"), "withoutDiving"),
    STATISTICS("statistiques", "statistics", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("statistics"), "statistics"),
    PERFECTS("Vous avez fait **: !!dés à coudre.", "You have done **: !!dés à coudre.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("DAC"), "DAC"),
    FAILS("Vous vous êtes fails **: !!fois.", "You have failes **: !!times.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("fails"), "fails"),
    WINS("Vous avez gagné **: !!parties.", "You won **: !!games.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("wins"), "wins"),
    LOSES("Vous avez perdu **: !!parties.", "You lost **: !!games.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("loses"), "loses"),
    GAMES("Vous avez fait **: !!parties.", "You played **: !!games.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("games"), "games"),
    JUMPS("Vous avez fait **: !!sauts.", "You jumped **: !!times.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("jumps"), "jumps"),
    PLAYER_NOT_FOUND("Joueur introuvable.", "Player not found.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("playerNotFound"), "playerNotFound"),
    BLOCK_CHOOSE("Block choisi.", "Block chosen.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("blockChoose"), "blockChoose"),
    BLOCK_CHOICE("Choix du bloc", "Block choice", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("blockChoice"), "blockChoice"),
    LEAVE_CHEST("Fermer", "Close", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("leaveChest"), "leaveChest"),
    NEXT("Suivant", "Next", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("next"), "next"),
    BACK("Retour", "Back", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("back"), "back"),
    SETTING_SAVED("Paramètre enregistré.", "Setting saved.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("settingSaved"), "settingSaved"),
    COME_JOIN("Tape §2/dac join ** §apour rejoindre la partie !", "Perform §2/dac join ** §ato join the game!", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("comeJoin"), "comeJoin"),
    STATISTICS_NOT_FOUND("Statistiques introuvables.", "Statistics not found.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("statisticsNotFound"), "statisticsNotFound"),
    WE_DOESNT_WORK_IN_1_13("Cette commande ne fonctionne pas en 1.13.", "This command doesn't work in 1.13.", ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("weDoesntWork"), "weDoesntWork"),
    DAC_PLAYER_RANKING("Classement des joueurs DAC", "DAC player ranking",  ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("dacRanking"), "dacRanking"),
    ARENA_NAME(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arena"), ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arena"), ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("arena"), "arena"),
    DAC(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("dac"), ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("dac"), ((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("dac"), "dac"),
    ;

    private String msg;
    private String msg1;
    private String msg2;
    private String path;
    Message(String msg, String msg1, String msg2, String path) {
        this.msg = msg.replace("&", "§");
        this.msg1 = msg1.replace("&", "§");
        this.msg2 = msg2.replace("&", "§")+"";
        this.path = path;
    }

    public String getMessage() {
        return msg2;
    }

    public String getPath() {
        return path;
    }

    public String getEnglishMessage() {
        return msg1;
    }

    public enum SignColorTag{

        OPEN_WAIT_LINE2_0(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.wait.line2.ifPlayersSize>=max/4*3"), "signColorTag.open.wait.line2.ifPlayersSize>=max/4*3"),
        OPEN_WAIT_LINE2_1(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.wait.line2.ifPlayersSize>=max/2"), "signColorTag.open.wait.line2.ifPlayersSize>=max/2"),
        OPEN_WAIT_LINE2_2(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.wait.line2.else"), "signColorTag.open.wait.line2.else"),
        OPEN_WAIT_LINE3_0(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.wait.line3.ifPlayersSize>=min"), "signColorTag.open.wait.line3.ifPlayersSize>=min"),
        OPEN_WAIT_LINE3_1(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.wait.line3.else"), "signColorTag.open.wait.line3.else"),
        OPEN_WAIT_LINE4(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.wait.line4"), "signColorTag.open.wait.line4"),
        OPEN_GAME_LINE2_0(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.game.line2.ifPlayersSize>=max/4*3"), "signColorTag.open.game.line2.ifPlayersSize>=max/4*3"),
        OPEN_GAME_LINE2_1(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.game.line2.ifPlayersSize>=max/2"), "signColorTag.open.game.line2.ifPlayersSize>=max/2"),
        OPEN_GAME_LINE2_2(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.game.line2.else"), "signColorTag.open.game.line2.else"),
        OPEN_GAME_LINE4(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.open.game.line4"), "signColorTag.open.game.line4"),
        CLOSE_LINE2(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("signColorTag.close.line2"), "signColorTag.close.line2");

        private String colorTag;
        private String path;
        SignColorTag(String string, String path) {
            this.colorTag = string.replace("&", "§");
            this.path = path;
        }

        public String getColorTag() {
            return colorTag;
        }

        public String getPath() {
            return path;
        }
    }

    public enum ScoreboardMessage{

        NAME(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("scoreboard.name"), "scoreboard.name"),
        NAME_TAG(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("scoreboard.nameTag"), "scoreboard.nameTag"),
        TIME_TAG(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("scoreboard.timeTag"), "scoreboard.timeTag"),
        JUMPER_TAG(((DacPluginV1_12) DacPlugin.INSTANCE.getDacPlugin()).configurations.getMessages().getMessages().getString("scoreboard.jumperTag"), "scoreboard.jumperTag"),
        ;

        private String msg;
        private String path;
        ScoreboardMessage(String string, String path) {
            this.msg = string.replace("&", "§");
            this.path = path;
        }

        public String getMessage() {
            return msg;
        }

        public String getPath() {
            return path;
        }
    }
}
