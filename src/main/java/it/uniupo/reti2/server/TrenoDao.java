package it.uniupo.reti2.server;

import it.uniupo.reti2.model.Passeggero;
import it.uniupo.reti2.model.Treno;
import it.uniupo.reti2.utils.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TrenoDao {
	
    public List<Passeggero> getAllPasseggeroTreno(String treno) {
        final String sql = "SELECT id, treno, bici FROM passeggeri WHERE treno = ?";

        List<Passeggero> passeggeri = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, treno);
            
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Passeggero passeggero = new Passeggero(rs.getInt("id"), rs.getString("treno"), rs.getInt("bici"));
                passeggeri.add(passeggero);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passeggeri;
    }
    
    public List<Passeggero> getAllPasseggeroTrenoBici(String treno) {
        final String sql = "SELECT id, treno, bici FROM passeggeri WHERE treno = ? AND bici = 1";

        List<Passeggero> passeggeri = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, treno);
            
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Passeggero passeggero = new Passeggero(rs.getInt("id"), rs.getString("treno"), rs.getInt("bici"));
                passeggeri.add(passeggero);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passeggeri;
    }
    
	public List<Passeggero> getAllPasseggero() {
        final String sql = "SELECT id, treno, bici FROM passeggeri";

        List<Passeggero> passeggeri = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Passeggero passeggero = new Passeggero(rs.getInt("id"), rs.getString("treno"), rs.getInt("bici"));
                passeggeri.add(passeggero);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passeggeri;
    }
    
    public List<Treno> getAllTreno() {
        final String sql = "SELECT id, stazione_partenza, orario_partenza, data_partenza, stazione_destinazione, orario_arrivo, data_arrivo, durata, tariffa, pieno, pieno_bici, posti, posti_bici FROM treni";

        List<Treno> treni = new LinkedList<>();//con arraylist?

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Treno treno = new Treno(rs.getInt("id"), rs.getString("stazione_partenza"), rs.getString("orario_partenza"), rs.getString("data_partenza"), rs.getString("stazione_destinazione"), rs.getString("orario_arrivo"), rs.getString("data_arrivo"), rs.getString("durata"), rs.getString("tariffa"), rs.getInt("pieno"), rs.getInt("pieno_bici"), rs.getInt("posti"), rs.getInt("posti_bici"));
                treni.add(treno);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treni;
    }

    public Passeggero getPasseggero(int id){
    	Passeggero passeggero = null;
        
    	final String sql = "SELECT id, treno, bici FROM passeggeri WHERE id = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
            	passeggero = new Passeggero(rs.getInt("id"), rs.getString("treno"), rs.getInt("bici"));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passeggero;
    }
    
    //Prende in ingresso il ? della riga sql
    public Treno getTreno(String orario) {
		Treno treno = null;
		
        final String sql = "SELECT id, stazione_partenza, orario_partenza, data_partenza, stazione_destinazione, orario_arrivo, data_arrivo, durata, tariffa, pieno, pieno_bici, posti, posti_bici FROM treni WHERE orario_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, orario);//setta il ? nella riga sql

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
            	//devono combaciare con la classe Treno
                treno = new Treno(rs.getInt("id"), rs.getString("stazione_partenza"), rs.getString("orario_partenza"), rs.getString("data_partenza"), rs.getString("stazione_destinazione"), rs.getString("orario_arrivo"), rs.getString("data_arrivo"), rs.getString("durata"), rs.getString("tariffa"), rs.getInt("pieno"), rs.getInt("pieno_bici"), rs.getInt("posti"), rs.getInt("posti_bici"));  
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treno;
	}

    public void addPasseggero(Passeggero passeggero) {
        final String sql = "INSERT INTO passeggeri(id, treno, bici) VALUES (?, ?, ?)";//passeggeri al posto di prenotazioni

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, passeggero.id);
            st.setString(2, passeggero.treno);
            st.setInt(3, passeggero.bici);

            st.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addTreno(Treno treno) {
        final String sql = "INSERT INTO treni(id, stazione_partenza, orario_partenza, data_partenza, stazione_destinazione, orario_arrivo, data_arrivo, durata, tariffa, pieno, pieno_bici, posti, posti_bici) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, treno.id);
            st.setString(2, treno.stazionePartenza);
            st.setString(3, treno.orarioPartenza);
            st.setString(4, treno.dataPartenza);
            st.setString(5, treno.stazioneDestinazione);
            st.setString(6, treno.orarioArrivo);
            st.setString(7, treno.dataArrivo);
            st.setString(8, treno.durata);
            st.setString(9, treno.tariffa);
            st.setInt(10, treno.pieno);
            st.setInt(11, treno.pienoBici);
            st.setInt(12, treno.posti);
            st.setInt(13, treno.postiBici);

            st.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean updateTrenoPieno(String treno) {
		
        final String sql = "UPDATE treni SET pieno = 1 WHERE orario_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, treno);

            st.executeUpdate();//or st.executeQuery();
            conn.close();
            
        } catch (SQLException e) {
            return false;
        }
        return true;
	}
    
    public boolean updateTrenoPienoBici(String treno) {
		
        final String sql = "UPDATE treni SET pieno_bici = 1 WHERE orario_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, treno);

            st.executeUpdate();//or 
            //st.executeQuery();
            conn.close();
            
        } catch (SQLException e) {
            return false;
        }
        return true;
	}
    
    public int getTrenoPosti(String orario) {
		int posti = 0;
		
        final String sql = "SELECT posti FROM treni WHERE orario_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, orario);

            ResultSet rs = st.executeQuery();

            posti = rs.getInt("posti");  
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posti;
	}
    
    public int getTrenoId(String orario) {
		int id = 0;
		
        final String sql = "SELECT id FROM treni WHERE orario_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, orario);

            ResultSet rs = st.executeQuery();

            id = rs.getInt("id");  
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
	}
    
    public int getTrenoPostiBici(String orario) {
		int postiBici = 0;
		
        final String sql = "SELECT posti_bici FROM treni WHERE orario_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, orario);

            ResultSet rs = st.executeQuery();

            postiBici = rs.getInt("posti_bici");  
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postiBici;
	}
    
    public int getTrenoPieno(String orario) {
		int pieno = 0;
		
        final String sql = "SELECT pieno FROM treni WHERE orario_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, orario);

            ResultSet rs = st.executeQuery();

            pieno = rs.getInt("pieno");  
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pieno;
	}
    
    public int getTrenoPienoBici(String orario) {
		int pienoBici = 0;
		
        final String sql = "SELECT pieno_bici FROM treni WHERE orario_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, orario);

            ResultSet rs = st.executeQuery();

            pienoBici = rs.getInt("pieno_bici");  
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pienoBici;
	}
    
    public boolean deleteAllTreno(String stazione) {
		
        final String sql = "DELETE FROM treni WHERE stazione_partenza = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, stazione);

            st.executeUpdate();
            conn.close();
            
        } catch (SQLException e) {
            return false;
        }
        return true;
	}
    
    public boolean deletePasseggero(String id) {
		
        final String sql = "DELETE FROM passeggeri WHERE id = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, id);

            //st.executeUpdate();
            st.executeQuery();
            conn.close();
            
        } catch (SQLException e) {
            return false;
        }
        return true;
	}

    public boolean deleteAllPasseggero() {
		
        final String sql = "DELETE FROM passeggeri";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            st.executeUpdate();
            conn.close();
            
        } catch (SQLException e) {
            return false;
        }
        return true;
	}
    
}
