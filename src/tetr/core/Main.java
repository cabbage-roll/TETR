package tetr.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import tetr.core.minecraft.CommandTetr;
import tetr.core.minecraft.Room;
import tetr.core.minecraft.functions.Functions;
import tetr.core.minecraft.functions.Functions_1_10_R1;
import tetr.core.minecraft.functions.Functions_1_11_R1;
import tetr.core.minecraft.functions.Functions_1_12_R1;
import tetr.core.minecraft.functions.Functions_1_13_R1;
import tetr.core.minecraft.functions.Functions_1_13_R2;
import tetr.core.minecraft.functions.Functions_1_14_R1;
import tetr.core.minecraft.functions.Functions_1_15_R1;
import tetr.core.minecraft.functions.Functions_1_16_R1;
import tetr.core.minecraft.functions.Functions_1_16_R2;
import tetr.core.minecraft.functions.Functions_1_16_R3;
import tetr.core.minecraft.functions.Functions_1_8_R1;
import tetr.core.minecraft.functions.Functions_1_8_R2;
import tetr.core.minecraft.functions.Functions_1_8_R3;
import tetr.core.minecraft.functions.Functions_1_9_R1;
import tetr.core.minecraft.functions.Functions_1_9_R2;
import tetr.core.minecraft.menus.Listen;
import tetr.core.normal.Table;
import tetr.core.normal.Window;

public class Main extends JavaPlugin implements Listener {

    public static boolean noteBlockAPIIsPresent;
    public static boolean netherBoardIsPresent;

    public static JavaPlugin plugin;
    public static ConsoleCommandSender console;

    public static LinkedHashMap<String, Room> roommap = new LinkedHashMap<String, Room>();
    public static HashMap<Player, Room> inwhichroom = new HashMap<Player, Room>();

    public static HashMap<Player, String> lastui = new HashMap<Player, String>();
    public static HashMap<Player, Integer> joinroompage = new HashMap<Player, Integer>();

    public static HashMap<Player, Integer> skineditorver = new HashMap<Player, Integer>();
    public static HashMap<Player, ItemStack[]> skinmap = new HashMap<Player, ItemStack[]>();

    public static void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean allowUnsafe(CommandSender sender) {
        if (Constants.iKnowWhatIAmDoing && (sender.hasPermission("tetr.developer"))) {
            return true;
        }
        return false;
    }

    public static Functions functions;

    public static int numberofsongs;
    String[] pathnames;
    String xd;
    static Song[] songarray;
    private static String version;

    @Override
    public void onEnable() {
        long timeStart = System.currentTimeMillis();

        plugin = this;

        try {
            LoadConfig.load(true);
        } catch (IOException e) {
            getLogger().severe("Error loading the config from inside jar.");
            // e.printStackTrace();
        }

        console = getServer().getConsoleSender();
        this.getCommand("tetr").setExecutor(new CommandTetr());

        // detect events
        getServer().getPluginManager().registerEvents(new Listen(), this);

        if (getServer().getPluginManager().getPlugin("NoteBlockAPI") == null) {
            getLogger().severe("NoteBlockAPI not found, server will throw shitload of errors. Beware");
            noteBlockAPIIsPresent = false;
        } else {
            getLogger().info("NoteBlockAPI OK.");
            noteBlockAPIIsPresent = true;
        }

        if (getServer().getPluginManager().getPlugin("Netherboard") == null) {
            getLogger().severe("Netherboard not found, server will throw shitload of errors. Beware");
            netherBoardIsPresent = false;
        } else {
            getLogger().info("Netherboard OK.");
            netherBoardIsPresent = true;
        }

        if (noteBlockAPIIsPresent) {
            // trash
            File f = new File(this.getDataFolder() + "/songs");
            f.mkdirs();
            numberofsongs = f.listFiles().length;
            if (numberofsongs > 0) {

                getLogger().info(numberofsongs + " song(s) found");

                pathnames = new String[numberofsongs];
                songarray = new Song[numberofsongs];
                pathnames = f.list();
                for (int i = 0; i < numberofsongs; i++) {
                    xd = this.getDataFolder() + "/songs/" + pathnames[i];
                    songarray[i] = NBSDecoder.parse(new File(xd));
                }

                Room.slist = new Playlist(songarray);
                // tRASH end
            } else {
                getLogger().info("No songs detected. Please add some songs!");
            }
        }

        if (checkVersion()) {
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            getLogger().severe("Unsupported server version");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            lastui.put(player, "home");

            if (!Main.skineditorver.containsKey(player)) {
                Main.skineditorver.put(player, 0);
            }

            initSkin(player);
        }

        long timeEnd = System.currentTimeMillis();

        long timeElapsed = timeEnd - timeStart;

        getLogger().info("Done. Time elapsed: " + timeElapsed + "ms");

        // add update checker
        // https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates/

    }

    private boolean checkVersion() {
        try {

            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        getLogger().info("Your server is running version " + version);

        if (version.equals("v1_8_R1")) {
            functions = new Functions_1_8_R1();
        } else if (version.equals("v1_8_R2")) {
            functions = new Functions_1_8_R2();
        } else if (version.equals("v1_8_R3")) {
            functions = new Functions_1_8_R3();
        } else if (version.equals("v1_9_R1")) {
            functions = new Functions_1_9_R1();
        } else if (version.equals("v1_9_R2")) {
            functions = new Functions_1_9_R2();
        } else if (version.equals("v1_10_R1")) {
            functions = new Functions_1_10_R1();
        } else if (version.equals("v1_11_R1")) {
            functions = new Functions_1_11_R1();
        } else if (version.equals("v1_12_R1")) {
            functions = new Functions_1_12_R1();
        } else if (version.equals("v1_13_R1")) {
            functions = new Functions_1_13_R1();
        } else if (version.equals("v1_13_R2")) {
            functions = new Functions_1_13_R2();
        } else if (version.equals("v1_14_R1")) {
            functions = new Functions_1_14_R1();
        } else if (version.equals("v1_15_R1")) {
            functions = new Functions_1_15_R1();
        } else if (version.equals("v1_16_R1")) {
            functions = new Functions_1_16_R1();
        } else if (version.equals("v1_16_R2")) {
            functions = new Functions_1_16_R2();
        } else if (version.equals("v1_16_R3")) {
            functions = new Functions_1_16_R3();
        }

        return functions != null;
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        lastui.put(player, "home");
        initSkin(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (lastui.containsKey(player)) {
            lastui.remove(player);
        }
        if (inwhichroom.containsKey(player)) {
            Main.inwhichroom.get(player).removePlayer(player);
        }
    }

    public void initSkin(Player player) {
        File customYml = new File(Main.plugin.getDataFolder() + "/userdata/" + player.getUniqueId() + ".yml");
        FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);
        ItemStack[] blocks = new ItemStack[17];
        blocks[0] = customConfig.getItemStack("blockZ");
        blocks[1] = customConfig.getItemStack("blockL");
        blocks[2] = customConfig.getItemStack("blockO");
        blocks[3] = customConfig.getItemStack("blockS");
        blocks[4] = customConfig.getItemStack("blockI");
        blocks[5] = customConfig.getItemStack("blockJ");
        blocks[6] = customConfig.getItemStack("blockT");
        blocks[7] = customConfig.getItemStack("background");
        blocks[8] = customConfig.getItemStack("garbage");
        blocks[9] = customConfig.getItemStack("ghostZ");
        blocks[10] = customConfig.getItemStack("ghostL");
        blocks[11] = customConfig.getItemStack("ghostO");
        blocks[12] = customConfig.getItemStack("ghostS");
        blocks[13] = customConfig.getItemStack("ghostI");
        blocks[14] = customConfig.getItemStack("ghostJ");
        blocks[15] = customConfig.getItemStack("ghostT");
        blocks[16] = customConfig.getItemStack("zone");
        skinmap.put(player, blocks);
        Main.skineditorver.put(player, customConfig.getInt("useSkinSlot"));
    }
    
    public static void main(String[] args) throws IOException {
        Table table = new Table();

        try {
            LoadConfig.load(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Constants.iKnowWhatIAmDoing) {
            try {
                Clip music = AudioSystem.getClip();

                music.open(AudioSystem.getAudioInputStream(new File("song36.wav")));

                music.start();

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        table.initGame();
        Window window = new Window(table);
        // Keyboard controls
        window.getFrame().addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    table.movePieceRelative(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    table.movePieceRelative(+1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    table.movePieceRelative(0, +1);
                    break;
                case KeyEvent.VK_SPACE:
                    table.hardDropPiece();
                    break;
                case KeyEvent.VK_Z:
                case KeyEvent.VK_Y:
                    table.rotatePiece(-1);
                    break;
                case KeyEvent.VK_X:
                    table.rotatePiece(+1);
                    break;
                case KeyEvent.VK_UP:
                    // 180
                    table.rotatePiece(+2);
                    break;
                case KeyEvent.VK_C:
                    table.holdPiece();
                    break;
                case KeyEvent.VK_SHIFT:
                    table.startZone();
                    break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}