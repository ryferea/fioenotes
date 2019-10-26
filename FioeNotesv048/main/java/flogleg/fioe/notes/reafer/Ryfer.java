package flogleg.fioe.notes.reafer;

public class Ryfer {
    public static boolean change = false;
    public static boolean ref = false;
    public static int id = 0;

    public static String parsing(String all,String old,String limit){
        String io = "";
        String[] oi = all.split(limit);
        for(int x = 0;x<oi.length;x++){
            if(oi[x].equals(old)){
                oi[x] = "";
            }
        }
        int o = 0;
        for(int x = 0;x<oi.length;x++){
            if(!oi[x].equals("")){
                if(o==0){
                    io = oi[x];
                }else{
                    io = io + limit + oi[x];
                }
                o++;
            }
        }
        return io;
    }

    public static  String implode(String oo ,String[] oz){
        String ul = "";
        if(oz.length>0&&oz.length<2){
            return oz[0];
        }
        for(int x = 0;x<oz.length;x++){
            if(x==0){
                ul = oz[x];
            }else{
                ul = ul + oo + oz[x];}
        }
        return ul;
    }
}
