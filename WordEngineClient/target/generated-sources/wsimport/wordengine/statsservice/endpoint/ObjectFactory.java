
package wordengine.statsservice.endpoint;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the wordengine.statsservice.endpoint package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetLetterStats_QNAME = new QName("http://statsservice.wordengine/", "getLetterStats");
    private final static QName _GetTopNWordsResponse_QNAME = new QName("http://statsservice.wordengine/", "getTopNWordsResponse");
    private final static QName _GetTopNWords_QNAME = new QName("http://statsservice.wordengine/", "getTopNWords");
    private final static QName _GetLetterStatsResponse_QNAME = new QName("http://statsservice.wordengine/", "getLetterStatsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: wordengine.statsservice.endpoint
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTopNWordsResponse }
     * 
     */
    public GetTopNWordsResponse createGetTopNWordsResponse() {
        return new GetTopNWordsResponse();
    }

    /**
     * Create an instance of {@link GetLetterStats }
     * 
     */
    public GetLetterStats createGetLetterStats() {
        return new GetLetterStats();
    }

    /**
     * Create an instance of {@link GetLetterStatsResponse }
     * 
     */
    public GetLetterStatsResponse createGetLetterStatsResponse() {
        return new GetLetterStatsResponse();
    }

    /**
     * Create an instance of {@link GetTopNWords }
     * 
     */
    public GetTopNWords createGetTopNWords() {
        return new GetTopNWords();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLetterStats }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://statsservice.wordengine/", name = "getLetterStats")
    public JAXBElement<GetLetterStats> createGetLetterStats(GetLetterStats value) {
        return new JAXBElement<GetLetterStats>(_GetLetterStats_QNAME, GetLetterStats.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTopNWordsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://statsservice.wordengine/", name = "getTopNWordsResponse")
    public JAXBElement<GetTopNWordsResponse> createGetTopNWordsResponse(GetTopNWordsResponse value) {
        return new JAXBElement<GetTopNWordsResponse>(_GetTopNWordsResponse_QNAME, GetTopNWordsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTopNWords }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://statsservice.wordengine/", name = "getTopNWords")
    public JAXBElement<GetTopNWords> createGetTopNWords(GetTopNWords value) {
        return new JAXBElement<GetTopNWords>(_GetTopNWords_QNAME, GetTopNWords.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLetterStatsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://statsservice.wordengine/", name = "getLetterStatsResponse")
    public JAXBElement<GetLetterStatsResponse> createGetLetterStatsResponse(GetLetterStatsResponse value) {
        return new JAXBElement<GetLetterStatsResponse>(_GetLetterStatsResponse_QNAME, GetLetterStatsResponse.class, null, value);
    }

}
