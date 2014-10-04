
package wordengine.statsservice.jaxws;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getStatsResponse", namespace = "http://statsservice.wordengine/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getStatsResponse", namespace = "http://statsservice.wordengine/")
public class GetStatsResponse {

    @XmlElement(name = "return", namespace = "")
    private HashMap<Character, Double> _return;

    /**
     * 
     * @return
     *     returns HashMap<Character,Double>
     */
    public HashMap<Character, Double> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(HashMap<Character, Double> _return) {
        this._return = _return;
    }

}
