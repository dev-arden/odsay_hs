package odsay_hs;


public class rank {
   boolean key;
   int id;
   int count;
   int time;
   int change;
   int total;

   rank() {
      this.key = true;
      this.id = 0;
      this.time = 0;
      this.count = 0;
      this.change = 0;
      this.total = 0;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getCount() {
      return this.count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public int getChange() {
      return this.count;
   }

   public void setChange(int change) {
      this.change = change;
   }

   public int getTime() {
      return this.time;
   }

   public void setTime(int time) {
      this.time = time;
   }
   
   public int getTotal() {
      return this.total;
   }

   public void setTotal0() {
      this.total = this.count + this.time;
   }
   
   public void setTotal1() {
      this.total = this.change * this.count + this.time;
   }

}