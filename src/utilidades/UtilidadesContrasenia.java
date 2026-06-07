package utilidades;

import org.mindrot.jbcrypt.BCrypt;

public class UtilidadesContrasenia {

    // Hashea una contraseña
    public static String hashearContraseña(String contraseniaBase) {
        return BCrypt.hashpw(contraseniaBase, BCrypt.gensalt());
    }

    // Verifica una contraseña con el hash almacenado
    public static boolean comprobarContrasenia(String contraseniaBase, String contraseniaHasheada) {
        return BCrypt.checkpw(contraseniaBase, contraseniaHasheada);
    }
}