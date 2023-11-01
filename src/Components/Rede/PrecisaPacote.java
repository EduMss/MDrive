package Components.Rede;
import java.io.Serializable;

public class PrecisaPacote implements Serializable{
    private boolean precisaPacotes = false;
    public PrecisaPacote(boolean precisaPacotes){
        this.precisaPacotes = precisaPacotes;
    }

    public boolean getPrecisaPacote(){
        return this.precisaPacotes;
    }
}
