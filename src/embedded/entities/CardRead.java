package embedded.entities;

import embedded.conn.Conn;

public class CardRead {
    private Conn conn = new Conn(9600,"/dev/cu.usbmodem14101");

    public Long getCardId (){
        return Converter(conn.receiveData());
    }

    public Conn getConn() {
        return conn;
    }

    private static long Converter(String hex){
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        long val = 0;
        for (int i = 0; i < hex.length(); i++)
        {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }
}
