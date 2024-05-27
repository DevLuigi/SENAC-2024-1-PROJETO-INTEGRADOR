package controller;

import java.awt.event.KeyEvent;

/**
* 
* @param p - objeto do tipo Validação
*/     
public class Validacao {
    public static boolean validaStringVazia(String texto) {
        return !texto.strip().equals("");
    }
    
    public static boolean validaString(KeyEvent evento, char letra) {
        if (((letra < 'A' || letra > 'z') && (letra != KeyEvent.VK_BACK_SPACE && letra != ' '))) {
            evento.consume();
            return false;
        }
        
        return true;
    }
    
    public static boolean validaNumero(KeyEvent evento, char letra) {        
        if (((letra < '0' || letra > '9') && (letra != KeyEvent.VK_BACK_SPACE))) {
            evento.consume();
            return false;
        }
        
        return true;
    }
    
    public static boolean validaConversaoInteiro(String numero) {
        try {
            return Integer.parseInt(numero) > 0;
        } catch(NumberFormatException err) {
            return false;
        }
    }
    
    public static boolean validaConversaoDecimal(String numero) {
        try {
            return Double.parseDouble(numero) > 0;
        } catch(NumberFormatException err) {
            return false;
        }
    }
}
