// "static void main" must be defined in a public class.
/*
Let's say you're given a list of exchange rates for currencies and cryptos.

USD BTC 10  (this means you can buy 10 USD by selling 1 BTC)
BTC USD 2  
CAD USD 1.25
USD CAD 2
ETH BTC 20
BTC ETH 100

You are also given a source and a target symbols, for example source=USD and target = BTC. Write a program that 
determines the best set of exchanges to buy target starting from source. 

BTC USD 100
CAD USD 2
BTC CAD 55

1 USD would get you 100 BTC directly
BUT you can sell 1 USD and get 2 CAD and then sell the CAD and get 110 BTC.
USD->BTC path is 100 exchange rate
USD->CAD->BTC is 110 so it's better

If it's possible to keep making money by going through cycle this is arbitrage. Your program should instead output "ARBITRAGE!" and provide a cycle that makes arbitrage possible.

BTC USD 10
USD BTC 10

Shortest paths
The basic version of the shortest path problem is the following:
Each edge has a numeric weight and the weight of a path is the SUM of the weight of the edges on it.
In this world, a NEGATIVE weight, means adding an edge can REDUCE the weight of a path and make it shorter. Hence why Dijsktra's doesn't work with negative weights.

In the currency problem, what is the weight of an edge? The exchange rate.
USD->BTC->CAD
What is the weight of a path? It's PRODUCT of the weights of the edges.
So in this case, what is the equivalent of a "negative" weight?
If weight is the SUM the a negative number is problematic in Dijsktra's.
If the weight is the PRODUCT what is the equivalent? A number between 0 and 1!

The input is:
A list of triples [[currency1, currency2, rate]]
and two strings that are symbols source and target
{
  {"USD", "BTC", 10},
  {"USD", "CAD", 0.5},
}
source="USD"
target="BTC"

Output should be either best exchange rate and the series of excahnges to achieve it, or "ARBITRAGE" followed by a cycle that achieves arbitrage.
*/

public class Main {
    public static void main(String[] args) {
        Exchange[] exchanges = new Exchange[]{new Exchange("BTC", "USD", 0.1), new Exchange("USD", "CAD", 0.5)};
        String src = "BTC";
        String dest = "CAD";
        execute(exchanges, src, dest);
        System.out.println("Hello world!");
    }
    
    public static void execute(Exchange[] exchanges, String src, String dest){
        HashMap<String, List<Exchange>> exchangeMap = new HashMap<>();
        for(Exchange exchange : exchanges){
            if(!exchangeMap.containsKey(exchange.from)){
                exchangeMap.put(exchange.from, new ArrayList<>());
            }
            if(!exchangeMap.containsKey(exchange.to)){
                exchangeMap.put(exchange.to, new ArrayList<>());
            }
            exchangeMap.get(exchange.from).add(exchange);
        }
        HashMap<String, Double> rates = new HashMap<>();
        for(String from : exchangeMap.keySet()){
            rates.put(from, Double.MIN_VALUE);
        }
        HashMap<String, String> parents = new HashMap<>();
        rates.put(src, 1.0);
        for(int i =0;i<exchangeMap.size()-1;i++){
            for(Exchange exchange : exchanges){
                if(rates.get(exchange.to)<rates.get(exchange.from)*exchange.rate){
                    rates.put(exchange.to,rates.get(exchange.from)*exchange.rate);
                    parents.put(exchange.to, exchange.from);
                }
            }
        }
        String c = dest;
        while(c!=null){
            System.out.println(c);
            c = parents.get(c);
        }
        
        for(String s : rates.keySet()){
            System.out.println(s + ":" + rates.get(s));
        }
    }
    
    static class Exchange{
        String from;
        String to;
        double rate;
        Exchange(String from, String to, double rate){
            this.from = from;
            this.to = to;
            this.rate = rate;
        }
    }
}
