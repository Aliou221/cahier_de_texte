package org.cahier_de_texte.dao.chef;

import org.cahier_de_texte.dao.DbConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CoursDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;
}