

public class Node implements Comparable, Cloneable {
    //public String itemNo;  
    //public int kırılganlık;
    public int location;
    public double weight;
    public double volume;
    public int quantity;
    public double totalWeight;
    public double totalVolume;
    public int x;
    public int y;
    public Node(int location, double weight, double volume, int quantity){
        this.location = location;
        this.findXY(location);
        this.weight = weight;
        this.volume = volume;
        this.quantity = quantity;
        this.totalWeight = weight*quantity;
        this.totalVolume = volume*quantity;
    }
    public void update(){
        this.findXY(location);
        this.totalWeight = this.weight*this.quantity;
        this.totalVolume = this.volume*this.quantity;
    }

    public Node copy(){
        Node newNode = new Node(this.location, this.weight, this.volume, this.quantity);
        return newNode;
    }

    @Override
    public int compareTo(Object o) {
        Node tmp = (Node)o;
        if (this.totalWeight > tmp.totalWeight) return 1;
        if (this.totalWeight < tmp.totalWeight) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "location=" + location +
                ", weight=" + weight +
                ", volume=" + volume +
                ", quantity=" + quantity +
                ", totalWeight=" + totalWeight +
                ", totalVolume=" + totalVolume +
                '}';
    }

    public void findXY(int location){
        this.x = findX(location);
        this.y = findY(location);
    }

    public int findX(int a){
        if(a==0 || a==101) return 450;
        if(a<=10) return 40;
        if(a<=20) return 140;
        if(a<=30) return 220;
        if(a<=40) return 320;
        if(a<=50) return 400;
        if(a<=60) return 500;
        if(a<=70) return 580;
        if(a<=80) return 680;
        if(a<=90) return 760;
        if(a<=100) return 860;
        return -1; // ERROR
    }

    public int findY(int loc){
        if(loc==0) return 300;
        if(loc==101) return 0;
        if(loc%10==0) return 15;
        return 15+30*(10-(loc%10));
    }

    public int findDistance(Node o){//
        //lokasyona gore ayni koridorda mi kontrol et
        int xDis = Math.abs(this.x-o.x);
        int yDis = -1;
        boolean isInSameHall = (int)(this.location-1)/20 == (int)(o.location-1)/20;
        if(isInSameHall){
            yDis = Math.abs(this.y-o.y);
        }
        else{
            int alt1 = (300-this.y)+(300-o.y);
            int alt2 = this.y + o.y;
            if(alt1 < alt2 )
                yDis = alt1;
            else
                yDis = alt2;
        }
        return xDis+yDis;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        Node clone = null; 
        try{
            clone = (Node)super.clone();
            clone.location = this.location;
            clone.weight = this.weight;
            clone.volume = this.volume;
            clone.quantity = this.quantity;
            clone.update();
        } catch (CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
        return clone;
    }
}
