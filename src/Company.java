public class Company {
    String name;
    double stockPrice, P_RATE, HIGHEST, LOWEST;
    double[] priceHistory = new double[5];
    int startingDay = 0, currentDay = 1;
    double success = 0.5;
    boolean rep, loop = false;

    public Company(String name){
        this.name = name;
        //generateTick();
        generateRep();
        stockPrice = rounding((Math.random()*95)+5);
        priceHistory[0] = stockPrice;
        HIGHEST = stockPrice;
        LOWEST = stockPrice;
    }
    private double rounding(double d){
        return Math.round(d * 100) / 100.0;
    }

    private void evaluateRep(){
        if (rep) P_RATE = 0.7;
        else P_RATE = 0.3;
    }


    private void generateRep(){
        double coinflip = Math.random();
        rep = coinflip > 0.3;
        evaluateRep();
    }

    public void progressToNextDay(){
        stockChange();
        priceHistory[currentDay] = stockPrice;
        currentDay++;
        if (loop) {
            if (startingDay < priceHistory.length) startingDay++;
            else startingDay = 0;
        }
        if (currentDay == priceHistory.length) {
            currentDay = 0;
            loop = true;
        }
        HIGHEST = 0;
        LOWEST = 999999999;
        for (double v : priceHistory) {
            if (v > HIGHEST) HIGHEST = v;
            if (v < LOWEST && v > 0) LOWEST = v;
        }
    }

    private void stockChange(){
        double coinflip = Math.random();
        if (coinflip < success) stockPrice += stockPrice*0.05*P_RATE;
        else {

            stockPrice -= stockPrice*0.05*(1-P_RATE);
        }
    }

    public double PriceDistance(double v) {
        return (v - LOWEST)/(HIGHEST - LOWEST);
    }
}