package botApplication.discApplication.librarys;

import botApplication.discApplication.librarys.certification.DiscCertificationLevel;
import botApplication.discApplication.librarys.item.monsters.Monster;
import botApplication.discApplication.librarys.job.UserJob;
import core.Engine;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DiscApplicationUser implements Serializable {

    private static final long serialVersionUID = 42L;

    private boolean edit = false;

    private String userName;
    private String ytPlaylist;
    private String userId;
    private boolean admin = false;
    private long telegramId;
    private DiscCertificationLevel discCertificationLevel;
    private ArrayList<String> servers = new ArrayList<>();
    private String lang = "en";

    private Date lastWorkTime;
    private Date lastDungeonVisit;
    private long coins = 20;
    private UserJob userJob;
    private int xp;
    private int level;

    private int maxMonsters = 10;
    private int maxItems = 30;

    private ArrayList<Monster> monsters = new ArrayList<>();

    private boolean saidHello = false;

    public DiscApplicationUser() {
        edit = true;
    }

    public DiscApplicationUser(User user, DiscCertificationLevel discCertificationLevel) {
        edit = true;
        this.userName = user.getName();
        this.userId = user.getId();
        this.discCertificationLevel = discCertificationLevel;
    }

    public void generateFromJSON(JSONObject obj, Engine engine){
        edit = false;
        userName = (String) obj.get("username");
        userId = (String) obj.get("userID");
        ytPlaylist = (String) obj.get("ytplaylist");
        admin = (boolean) obj.get("isBotAdmin");
        lang = (String) obj.get("lang");
        coins = (long) obj.get("coins");
        xp = Math.toIntExact((long) obj.get("xp"));
        level = Math.toIntExact((long) obj.get("level"));
        maxMonsters = Math.toIntExact((long) obj.get("maxMonsters"));
        maxItems = Math.toIntExact((long) obj.get("maxItems"));
    }

    public boolean isMonsterInvFull() {
        return monsters.size() >= maxMonsters;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        edit = true;
    }

    public String getYtPlaylist() {
        return ytPlaylist;
    }

    public void setYtPlaylist(String ytPlaylist) {
        this.ytPlaylist = ytPlaylist;
        edit = true;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        edit = true;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
        edit = true;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
        edit = true;
    }

    public DiscCertificationLevel getDiscCertificationLevel() {
        return discCertificationLevel;
    }

    public void setDiscCertificationLevel(DiscCertificationLevel discCertificationLevel) {
        this.discCertificationLevel = discCertificationLevel;
        edit = true;
    }

    public ArrayList<String> getServers() {
        edit = true;
        return servers;
    }

    public void setServers(ArrayList<String> servers) {
        edit = true;
        this.servers = servers;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        edit = true;
        this.lang = lang;
    }

    public Date getLastWorkTime() {
        return lastWorkTime;
    }

    public void setLastWorkTime(Date lastWorkTime) {
        edit = true;
        this.lastWorkTime = lastWorkTime;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        edit = true;
        this.coins = coins;
    }

    public UserJob getUserJob() {
        edit = true;
        return userJob;
    }

    public void setUserJob(UserJob userJob) {
        edit = true;
        this.userJob = userJob;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        edit = true;
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        edit = true;
        this.level = level;
    }

    public void addCoins(int coins) {
        edit = true;
        this.coins += coins;
    }

    public void substractCoins(int coins) {
        edit = true;
        this.coins -= coins;
    }

    public boolean isSaidHello() {
        return saidHello;
    }

    public void setSaidHello(boolean saidHello) {
        this.saidHello = saidHello;
    }

    public ArrayList<Monster> getMonsters() {
        edit = true;
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        edit = true;
        this.monsters = monsters;
    }

    public void addMonster(Monster m) throws Exception {
        edit = true;
        if (monsters.size() >= maxMonsters) {
            throw new Exception("To many monsters");
        }
        monsters.add(m);
    }

    public int getMaxMonsters() {
        return maxMonsters;
    }

    public void setMaxMonsters(int maxMonsters) {
        edit = true;
        this.maxMonsters = maxMonsters;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(int maxItems) {
        edit = true;
        this.maxItems = maxItems;
    }

    public Date getLastDungeonVisit() {
        return lastDungeonVisit;
    }

    public void setLastDungeonVisit(Date lastDungeonVisit) {
        edit = true;
        this.lastDungeonVisit = lastDungeonVisit;
    }

    public void upgrade() {
        edit = true;
        maxItems = 30;
    }

    public boolean isEdit() {
        return edit;
    }
}