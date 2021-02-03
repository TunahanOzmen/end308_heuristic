
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {

    static final int MAX_WEIGHT = 120;
    static final int MAX_VOLUME = 250000;

    //returns nearest available cell
    //en yakindaki mal alinabilecek hucre
    public static Node findNearest(ArrayList<Node> list, double availableWeight, double availableVolume,Node currCell){
       Node min = new Node(-1,0,0,0);//DUMMY NODE
        ArrayList<Node> availableList = new ArrayList<>();
        for(Node node : list){//en az 1 tane alinanlar listesi
            if( node.weight <= availableWeight && node.volume <= availableVolume ){
                availableList.add(node);
            }
        }
        int minDistance = 5000;
        for(Node node: availableList){//listeden en yakini
            int distance = currCell.findDistance(node);
            if (distance==0) continue;
            if(minDistance>distance){
                minDistance=distance;
                min = node;
            }
        }
        return min;
    }


    public static Node minWeightedOrder(ArrayList<Node> list){
        double minWeight= 50000;
        Node min = null;
        for(Node node: list){//listeden en yakini
            if(node.weight < minWeight){
                minWeight = node.weight;
                min = node;
            }
        }
        return min;
    }


    public static double findObjFunc(ArrayList<Node> list){
        Pallet pallet = new Pallet();
        pallet.volume=0;
        pallet.weight=0;
        double value = 0;
        for(int i = 0 ; i < list.size(); i++){
            Node node = list.get(i);
            if(node.location == 101){
                pallet.weight = 0;
                continue;
            }
            int distance = node.findDistance(list.get(i+1));
            pallet.weight += node.weight*node.quantity;
            value += pallet.weight*distance;
        }

        return value;
    }


    public static void print(ArrayList<Node> list){
        Pallet pallet = new Pallet();
        pallet.volume=0;
        pallet.weight=0;
        double value = 0;
        for(int i = 0 ; i < list.size(); i++){
            Node node = list.get(i);
            if(node.location == 101){
                pallet.weight = 0;
                pallet.volume = 0;
                System.out.println("\n----EXİT---- pallet weight: "+node.weight+" pallet volume: "+node.volume+"\n");
                continue;
            }
            int distance = node.findDistance(list.get(i+1));
            pallet.weight += node.weight*node.quantity;
            pallet.volume += node.volume*node.quantity;
            value += pallet.weight*distance;
            System.out.println(node);
            System.out.println("Pallet Weight: "+pallet.weight+" Pallet Volume: "+pallet.volume+" Distance: "+distance+" value: "+distance*pallet.weight);
            System.out.println();
        }

        System.out.println("Objective Function Value: "+value/100);
    }


    public static ArrayList<Node> heuristic(ArrayList<Node> list){
        if(list.size()<=3)//0 x 101
            return list;
        Random rand = new Random();
        int rand1, rand2;
        int tmp = list.size()-2;
        for(int i = 0 ; i < tmp*(tmp-1)/2 ; i++) {
            rand1 = rand.nextInt((list.size() - 2)) + 1;
            rand2 = rand.nextInt((list.size() - 2)) + 1;
            while(rand1==rand2)
                rand2 = rand.nextInt((list.size() - 2)) + 1;

            ArrayList<Node> retList = new ArrayList<>();
            for(Node node : list){
                retList.add(new Node(node.location,node.weight,node.volume,node.quantity));
            }

            Node tmpNode =  retList.get(rand1).copy();

            retList.get(rand1).weight = list.get(rand2).weight ;
            retList.get(rand1).volume = list.get(rand2).volume ;
            retList.get(rand1).location = list.get(rand2).location;
            retList.get(rand1).quantity = list.get(rand2).quantity ;

            retList.get(rand2).weight = tmpNode.weight ;
            retList.get(rand2).volume = tmpNode.volume ;
            retList.get(rand2).location = tmpNode.location ;
            retList.get(rand2).quantity = tmpNode.quantity ;

            retList.get(rand1).update();retList.get(rand2).update();

            if(findObjFunc(list) > findObjFunc(retList)){
                list = (ArrayList<Node>)retList.clone();
            }
        }

        return list;
    }



    public static void main(String[] args) {
        //CONSTRUCTION HEURISTIC

        ArrayList<Node> orderList = new ArrayList<>();
        ArrayList<Node> path = new ArrayList<>();
        Node currCell = new Node (0, 0, 0, 0);//giris;


        //siparis 1

        orderList.add(new Node(2,0.8,22988.16,1));
        orderList.add(new Node(4,4,17840.088,3));
        orderList.add(new Node(17,12.3,13243.12,1));
        orderList.add(new Node(97,4.4,14857.535,3));
        orderList.add(new Node(20,16.2,25969.17925,1));
        orderList.add(new Node(38,18.3,27747.888,1));
        orderList.add(new Node(59,20.4,30066.4,1));
        orderList.add(new Node(8,6.9,10671.1605,3));
        orderList.add(new Node(74,12.2,21044.76,3));
        orderList.add(new Node(87,12.2,17233.692,4));
        orderList.add(new Node(93,3.9,18146.7,20));
        orderList.add(new Node(24,12.2,25969.17925,10));
        orderList.add(new Node(100,12.2,20808.645,10));


        //siparis 5
        /*
        orderList.add(new Node(70,20.3,29358.672,15));
        orderList.add(new Node(15,9.4,19280.592,10));
        orderList.add(new Node(53,16.3,24433.92,9));
        orderList.add(new Node(39,20.3,30025.112,8));
        orderList.add(new Node(1,0.6,31497.102,5));
        orderList.add(new Node(100,12.2,20808.645,5));
        orderList.add(new Node(48,20.3,23798.64,2));
        orderList.add(new Node(24,12.2,25969.17925,1));
        orderList.add(new Node(29,16.3,19882.35,1));
        */
        //siparis 4
/*
        orderList.add(new Node(29,	16.3,	19882.35,	1));
        orderList.add(new Node(96,	4.4,	37302.72,	3));
        orderList.add(new Node(54,	16.4,	24751.18122,	1));
        orderList.add(new Node(31,	12.2,	20809.467,	1));
        orderList.add(new Node(73,	11.6,	23200.767,	1));
        orderList.add(new Node(36,	16.4,	22927.632,	1));
        orderList.add(new Node(55,	17.7,	29248.70044,	2));
        orderList.add(new Node(22,	6.3,	12806.112,	2));
        orderList.add(new Node(46,	19.3,	28259.77944,	1));
        orderList.add(new Node(77,	15.6,	26541.28161,	2));
        orderList.add(new Node(94,	4,	16933.75,	1));
        orderList.add(new Node(62,	12.9,	25082.12626,	2));
        orderList.add(new Node(63,	15.6,	26541.28161,	1));
        orderList.add(new Node(42,	16.2,	26858.86972,	3));
        orderList.add(new Node(80,	16.4,	21579.36,	1));
        orderList.add(new Node(44,	16.4,	34865.424,	6));
        orderList.add(new Node(90,	16.2,	26452.65,	1));
        orderList.add(new Node(74,	12.2,	21044.76,	1));
        orderList.add(new Node(92,	3.2,	15892.24,	15));
        orderList.add(new Node(89,	15.6,	26541.28161,	1));
        orderList.add(new Node(11,  4.3,    13258.7,    1));
        orderList.add(new Node(33,  15.6,   26541.3,    4));
        orderList.add(new Node(66,  16.3,   22289.4,    1));
        orderList.add(new Node(25,  12.3,   21248.656,  1));
        orderList.add(new Node(32,  15.4,   26432.448,  1));
        orderList.add(new Node(76,  15.4,   26827.35,   22));
        orderList.add(new Node(88,  12.4,   21602.7,    1));
        orderList.add(new Node(47,	20.3,	34602.696,   1));
        orderList.add(new Node(71,	4.4,	13308.68,    4));
        orderList.add(new Node(2,	0.8,	22988.16,     1));
        orderList.add(new Node(13,	4.4,	13186.8,	    2));
        orderList.add(new Node(12,	4.4,	13891.584,	2));
*/

        System.out.println("******************CONSTRUCTION PART******************\n");
        path.add(currCell);
        Pallet pallet = new Pallet();
        while(!orderList.isEmpty()){
            boolean isItFirst=true;
            while(true){
                Node nearest = null;
                if(isItFirst){//pallet goes to the min weighted cell, only for first time each tour
                    isItFirst=false;
                    nearest = minWeightedOrder(orderList);
                }
                else{//pallet goes to the nearest order
                    nearest = findNearest(orderList, MAX_WEIGHT-pallet.weight, MAX_VOLUME-pallet.volume, currCell);
                }
                //alabilecegi hic kalmadiysa exite git, paleti sifirla
                if(nearest.location == -1)//no available cell, go to exit
                    break;
                //Pallet belli bir agirligi astiysa ve cikisa, gidecegi yerden daha yakinsa cikmayi tercih etsin
                if(pallet.weight>= 100 && currCell.findDistance(new Node(101,0,0,0)) <= currCell.findDistance(nearest) )
                    break;
                currCell = nearest; // to calculate nearest cell, we should know current cell
                // kac tane alacagini hesapla
                int maxQuantityW = (int)((MAX_WEIGHT-pallet.weight)/nearest.weight);
                maxQuantityW = nearest.quantity>maxQuantityW?maxQuantityW:nearest.quantity;//select min
                int maxQuantityV = (int)((MAX_VOLUME-pallet.volume)/nearest.volume);
                maxQuantityV = nearest.quantity>maxQuantityV?maxQuantityV:nearest.quantity;//select min
                int maxQuantity = maxQuantityW>maxQuantityV?maxQuantityV:maxQuantityW;//select min
                //pallete yukle
                pallet.weight += maxQuantity*nearest.weight;
                pallet.volume += maxQuantity*nearest.volume;
                // path'e kopyasını ekle
                path.add(new Node(nearest.location,nearest.weight,nearest.volume,maxQuantity));
                // Node update et (alinan kadarini azalt)
                nearest.quantity -= maxQuantity;
                nearest.update();
                // quantity == 0 ise listeden cikart
                if(nearest.quantity == 0)
                    orderList.remove(nearest);
            }
            path.add(new Node(101,pallet.weight,pallet.volume,0));
            pallet.volume=0;
            pallet.weight=0;
            path.add(new Node(0,0,0,0));
            currCell = new Node (0, 0, 0, 0);//giris, start a new route;
        }
        path.remove(path.size()-1);
        print(path);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("******************IMPROVEMENT PART******************\n");
        //IMPROVEMENT HEURISTIC
        double value = 100000;
        ArrayList<Node> minPath = new ArrayList<>();
        for(int i = 0 ; i < 100  ; i++){

            ArrayList<Node> subtour = new ArrayList<>();
            ArrayList<Node> newPath = new ArrayList<>();
            for(Node node : path){
                subtour.add(node);
                if(node.location == 101){
                    ArrayList<Node> newSubtour = (ArrayList<Node>)heuristic(subtour).clone();
                    for(Node newNode : newSubtour)
                        newPath.add(newNode.copy());
                    subtour = new ArrayList<>();
                }
            }

            //System.out.println(i+" : "+findObjFunc(newPath)/100);

            if(findObjFunc(newPath)/100 < value){
                minPath.clear();
                value = findObjFunc(newPath)/100;
                for(Node node : newPath){
                    minPath.add(new Node(node.location,node.weight,node.volume,node.quantity));
                }
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
        print(minPath);

    }
}
