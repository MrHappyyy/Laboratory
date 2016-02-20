package client;

import data_base.*;
import java.io.*;
import java.net.Socket;

public class DataExchange {
    private Socket socket;
    private DataInputStream io;
    private DataOutputStream ou;

    /*private ObjectOutputStream objOu;
    private ObjectInputStream objIo;*/

    public DataExchange(Socket socket) {
        this.socket = socket;

        try {
            io = new DataInputStream(socket.getInputStream());
            ou = new DataOutputStream(socket.getOutputStream());

            /*objOu = new ObjectOutputStream(socket.getOutputStream());
            objIo = new ObjectInputStream(socket.getInputStream());*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transferString(String string) {
        try {
            ou.writeUTF(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String acceptString() {
        try {
            return io.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void transferInt(int i) {
        try {
            ou.writeInt(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int acceptInt() {
        try {
            return io.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void transferDouble(double d) {
        try {
            ou.writeDouble(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double acceptDouble() {
        try {
            return io.readDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void transferBoolean(boolean b) {
        try {
            ou.writeBoolean(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean acceptBoolean() {
        try {
            return io.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void transferProductEntity(ProductEntity entity) {

        try {
            ou.writeInt(entity.getProductId());
            ou.writeUTF(entity.getProductName());
            ou.writeInt(entity.getGroupId());
            ou.writeInt(entity.getSubGroupId());
            ou.writeDouble(entity.getAmount());
            ou.writeDouble(entity.getPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ProductEntity acceptProductEntity() {

        try {
            ProductEntity entity = new ProductEntity();
            entity.setProductId(io.readInt());
            entity.setProductName(io.readUTF());
            entity.setGroupId(io.readInt());
            entity.setSubGroupId(io.readInt());
            entity.setAmount(io.readDouble());
            entity.setPrice(io.readDouble());
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void transferGroupEntity(GroupEntity entity) {

        try {
            ou.writeInt(entity.getGroupId());
            ou.writeUTF(entity.getGroupName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GroupEntity acceptGroupEntity() {

        try {
            GroupEntity entity = new GroupEntity();
            entity.setGroupId(io.readInt());
            entity.setGroupName(io.readUTF());
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void transferSubGroupEntity(SubGroupEntity entity) {

        try {
            ou.writeInt(entity.getSubGroupId());
            ou.writeUTF(entity.getSubGroupName());
            ou.writeInt(entity.getGroupId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SubGroupEntity acceptSubGroupEntity() {

        try {
            SubGroupEntity entity = new SubGroupEntity();
            entity.setSubGroupId(io.readInt());
            entity.setSubGroupName(io.readUTF());
            entity.setGroupId(io.readInt());
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void transferStatisticsEntity(StatisticsEntity entity) {

        try {
            ou.writeInt(entity.getId());
            ou.writeUTF(entity.getName());
            ou.writeUTF(entity.getGroup());
            ou.writeUTF(entity.getSubGroup());
            ou.writeDouble(entity.getPrice());
            ou.writeDouble(entity.getAmountSell());
            ou.writeDouble(entity.getPriceSell());
            ou.writeUTF(entity.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StatisticsEntity acceptStatisticsEntity() {

        try {
            StatisticsEntity entity = new StatisticsEntity();
            entity.setId(io.readInt());
            entity.setName(io.readUTF());
            entity.setGroup(io.readUTF());
            entity.setSubGroup(io.readUTF());
            entity.setPrice(io.readDouble());
            entity.setAmountSell(io.readDouble());
            entity.setPriceSell(io.readDouble());
            entity.setData(io.readUTF());
            return entity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void flush() {
        try {
            ou.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public void transferObject(Object o) {
        try {
            objOu.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object acceptObject() {
        try {
            return objIo.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void transferListObject(List<Object> list) {
        try {
            ou.writeInt(list.size());

            for (int i = 0; i < list.size(); i++) {
                 objOu.writeObject(list.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Object> acceptListObject() {
        List<Object> list = new ArrayList<Object>();
        try {
            int size = io.readInt();

            for (int i = 0; i < size; i++) {
                list.add(objIo.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }*/
}
