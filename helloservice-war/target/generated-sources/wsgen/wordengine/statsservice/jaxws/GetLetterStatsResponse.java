
package wordengine.statsservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getLetterStatsResponse", namespace = "http://statsservice.wordengine/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLetterStatsResponse", namespace = "http://statsservice.wordengine/")
public class GetLetterStatsResponse {

    @XmlElement(name = "return", namespace = "")
    private List<Double> _return;

    /**
     * 
     * @return
     *     returns List<Double>
     */
    public List<Double> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<Double> _return) {
        this._return = _return;
    }

}
