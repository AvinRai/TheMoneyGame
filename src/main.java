import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
public class main extends PApplet{
    PFont font;
    double money, knowledge, income, savings;
    int screen, popup, day;
    PImage titleScreen, tutorial, handbook, map, market, nextDay, bank, endScreen, endDay;
    String[] compNames;
    Company[] companies;
    boolean jobDone, schoolDone;
    int currComp;
    int[] stocksOwned;
    double[] endDayStats;
    String[] endDayCosts;

    public void settings(){
        size(1600, 900);
    }
    public void setup(){
        font = createFont("assets/Dimbo.ttf", 32);
        day = 1;
        income = 100;
        money = 1000;
        popup = 0;
        savings = 0;
        screen = 0;
        bank = loadImage("assets/Bank.png");
        endDay = loadImage("assets/End Day.png");
        endScreen = loadImage("assets/End Screen.png");
        handbook = loadImage("assets/Handbook.png");
        map = loadImage("assets/Map.png");
        market = loadImage("assets/Market.png");
        nextDay = loadImage("assets/Next Day.png");
        titleScreen = loadImage("assets/Title.png");
        tutorial = loadImage("assets/Tutorial.png");
        currComp = 0;
        jobDone = true;
        schoolDone = true;
        endDayStats = new double[5];

        endDayCosts = new String[] {"Tax (7%)", "Needs", "Wants", "Emergencies", "Total Money Remaining"};

        compNames = new String[] {"Pear", "Macrohard", "HandScript", "Boogle" , "Du Industries", "Rai Enterprises"};
        companies = new Company[compNames.length];
        stocksOwned = new int[companies.length];
        for (int i = 0; i < compNames.length; i ++){
            companies[i] = new Company(compNames[i]);
        }
    }

    public void draw(){
        clear();
        textFont(font);
        //menu
        background(0);
        if (screen == 0) { // Display Menu
            image(titleScreen,0,0);
        }

        if (screen == 1) { // Instructions
            image(tutorial,0,0);
        }

        if (screen == 2) { // In-depth on personal finance
            image(handbook, 0, 0);
        }

        //game
        if (screen == 5) { // Central Map
            image(map, 0, 0);
            image(nextDay,0,0);

            if (mouseX >= 1060 && mouseX <= 1376 && mouseY >= 172 && mouseY <= 405 && popup == 0) msg(245,245, 245, 75, "Income: $" + rounding(income), 20, 150); // income
            if (mouseX >= 730 && mouseX <= 1033 && mouseY >= 496 && mouseY <= 703 && popup == 0) msg(245,245,245,75,"Knowledge: " + rounding(knowledge) + "x",1115,880); // player knowledge

            if (popup == 1) { // Market
                image(market, 0, 0);

                msg(255,255,255,60,companies[currComp].name,470,380); // Name
                msg(255,255,255,60,"$" + rounding(companies[currComp].stockPrice),400,450); // Share Price
                String comPer = isGood(companies[currComp].rep);
                msg(255,255,255,60,comPer,650,515); // Company Performance

                if (day > 1) {
                    Company c = companies[currComp];
                    int start = c.startingDay;
                    int xLoc = 800;
                    for (int i = 0; i < c.priceHistory.length; i++) {
                        fill(123, 186, 147);
                        int yLoc = (int) (750 - 343 * (c.PriceDistance(c.priceHistory[start])));
                        ellipse(xLoc, yLoc, 25, 25);
                        msg(255, 255, 255, 50, "$" + rounding(c.priceHistory[start]), xLoc, yLoc);
                        xLoc += 115;
                        start++;
                        if (start >= c.priceHistory.length) start = 0;
                    }
                }
            }

            if (popup == 2) { // Bank
                image(bank,0,0);

                msg(245,245,245,75,"" + rounding(money),322,397); // checking money
                msg(245,245,245,75,"" + rounding(savings),322,635); // checking money

                int firstComp = 350;
                for (int i : stocksOwned){
                    msg(245,245,245,75,"" + i,1130,firstComp); // checking money
                    firstComp += 80;
                }


            }

            msg(245,245,245,75,"Cash: $" + rounding(money),15,75); // player money
            msg(245,245,245,80,"Day: " + day,1300,75); // day

            if (popup == 4) {
                image(endDay,0,0);
                msg(245,245,245,100,"End of Day: " + (day - 1),628,150); // day
                msg(245, 245, 245, 90, "Expenses", 310, 250);
                int y = 350;
                for (int i = 0; i < endDayStats.length; i++){
                    msg(245,245,245,80, endDayCosts[i] + ": $" + rounding(endDayStats[i]), 310,y);
                    y+=100;
                }

            }
        }

        if (screen == 6){
            image(endScreen, 0, 0);
            msg(255,255,255,150,"" + day,970,580);
        }
        if (money <= 0) screen = 6;
    }

    private String isGood (boolean rep) {
        if (rep) return "Good";
        return "Bad";
    }

    public static void main(String[] args) {PApplet.main("main");}


    public void msg(int r, int g, int b, int s, String n, int x, int y){
        fill(r,g,b);
        textSize(s);
        text(n,x,y);
    }
    public void mouseReleased() {
        System.out.println(mouseX  + "   ,   " + mouseY);
        if (screen == 0) { // title screen
            if (mouseX >= 477 && mouseX <= 1126) {
                if (mouseY >= 420 && mouseY <= 475+71) screen = 5;
                if (mouseY >= 507+71 && mouseY <= 631+71) screen = 1;
            }
        }

        //back buttons for instructions and handbook
        if (screen == 1 || screen == 2) if (mouseX >= 21 && mouseX <= 168 && mouseY >= 22 && mouseY <= 135) screen -= 1;

        //to handbook from instructions
        if (screen == 1) if (mouseX >= 1200 && mouseX <= 1582 && mouseY >= 19.5 && mouseY <= 150) screen++;

        if (screen == 5) {
            if (popup == 0){
                // to market
                if (mouseX >= 170 && mouseX <= 410 && mouseY >= 255 && mouseY <= 490) {
                    popup = 1;
                }

                // to bank
                if (mouseX >= 620 && mouseX <= 923 && mouseY >= 96 && mouseY <= 303){
                    popup = 2;
                }

                // to job
                if (mouseX >= 1060 && mouseX <= 1376 && mouseY >= 172 && mouseY <= 405){
                    if (jobDone) money += income;
                    jobDone = false;
                }

                // to school
                if (mouseX >= 730 && mouseX <= 1033 && mouseY >= 496 && mouseY <= 703){
                    if (schoolDone) {
                        knowledge += rounding((Math.random()*0.5) + 0.5);
                        income += income * (knowledge/100);
                        income = rounding(income);
                    }
                    schoolDone = false;
                }

                // going to next day
                if (mouseX >= 0 && mouseX <= 400 && mouseY >= 500 && mouseY <= 900) {
                    dailyExpenses();
                    day ++;
                    for (Company c : companies) {
                        c.progressToNextDay();
                    }
                    jobDone = true;
                    schoolDone = true;
                    savings += rounding(savings * 0.01);
                    popup = 4;
                }
            }

            // exit popup
            if (mouseX >= 1400 && mouseX <= 1516 && mouseY >= 78 && mouseY <= 185) {
                if (popup != 0) popup = 0;
            }

            //Stocks CHECK
            if (popup == 1) {

                if (mouseY >= 212 && mouseY <= 271) {
                    if (mouseX >= 87 && mouseX <= 312) currComp = 0;
                    if (mouseX >= 328 && mouseX <= 551) currComp = 1;
                    if (mouseX >= 569 && mouseX <= 791) currComp = 2;
                    if (mouseX >= 807 && mouseX <= 1033) currComp = 3;
                    if (mouseX >= 1046 && mouseX <= 1275) currComp = 4;
                    if (mouseX >= 1287 && mouseX <= 1514) currComp = 5;
                }

                // buy button
                if (mouseX >= 116 && mouseX <= 537 && mouseY >= 570 && mouseY <= 678) {
                    money -= rounding(companies[currComp].stockPrice);
                    stocksOwned[currComp]++;
                }

                //sell
                if (mouseX >= 116 && mouseX <= 537 && mouseY >= 703 && mouseY <= 815) {
                    if (stocksOwned[currComp] > 0) {
                        money += rounding(companies[currComp].stockPrice);
                        stocksOwned[currComp]--;
                    }
                }
            }

            if (popup == 2) {
                // transfer to CHECK
                if (mouseX >= 106 && mouseX <= 405 && mouseY >= 746 && mouseY <= 818) {
                    if (money > 100) transferTo();
                }

                // transfer from
                if (mouseX >= 428 && mouseX <= 725 && mouseY >= 746 && mouseY <= 818) {
                    if (savings > 0) transferFrom();
                }
            }
        }
    }

    public void dailyExpenses(){
        double tax = rounding(income * 0.07);
        double wants = rounding(income * 0.3);
        double needs = rounding(income * 0.2);
        double emergency = randomEvent();

        endDayStats[0] = tax;
        endDayStats[1] = wants;
        endDayStats[2] = needs;
        endDayStats[3] = emergency;

        money -= rounding(tax + wants + needs + emergency);
        money = rounding(money);

        double stockTotal = 0;
        for (int i = 0; i < companies.length; i ++){
            stockTotal += companies[i].stockPrice * stocksOwned[i];
        }

        endDayStats[4] = money + savings + stockTotal;
    }
    public double randomEvent(){
        double rng = Math.random();
        if (rng > 0.9) return 300;
        else if (rng > 0.6) return 100;
        return 0;
    }
    public double rounding(double d){
        return Math.round(d * 100) / 100.0;
        // return ((int)(d*100))/100.0;
    }
    public void transferTo(){
        money -= 100;
        savings += 100;
    }
    public void transferFrom(){
        if (savings >= 100) {
            money += 100;
            savings -= 100;
        } else {
            money += savings;
            savings = 0;
        }
    }
}