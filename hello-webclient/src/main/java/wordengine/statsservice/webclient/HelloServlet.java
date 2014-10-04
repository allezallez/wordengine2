package wordengine.statsservice.webclient;

import wordengine.statsservice.endpoint.StatsServiceService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

@WebServlet(name="StatsServlet", urlPatterns={"/StatsServlet"})
public class HelloServlet extends HttpServlet {
    @WebServiceRef(wsdlLocation = 
      "http://54.69.79.91:8080/helloservice-war/StatsServiceService?WSDL")
    private StatsServiceService service;
   
    /** 
     * Processes requests for both HTTP <code>GET</code>
     *   and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Servlet StatsServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<form name=\"getStatsForm\" method = \"post\"  ");
            out.println("<p>" + getTopNWords(" A A A") + "</p>");
            out.println("</body>");
            out.println("</html>");
            
        }
    } 


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      String partialWord = request.getParameter("partial");
      PrintWriter writer= response.getWriter();

      if (partialWord == null) {
        writer.println(makeNicknames(request.getParameter("firstname").toUpperCase(),
                       request.getParameter("lastname").toUpperCase()));
      } else {
        writer.println(makeStatsReport(partialWord.toUpperCase()));
      }
      writer.close();
    }

    private String makeNicknames(String firstName, String lastName) {
      String htmlResponse = "<html><body>";
      htmlResponse += "<h2>Vowel Stripping Algorithm:</h2>";
      htmlResponse += "<p>" + stripLettersGetAutoComplete(firstName, true) + " " +
                      stripLettersGetAutoComplete(lastName, true) + "</p>";
      htmlResponse += "<h2>Consonant Stripping Algorithm:</h2>";
      htmlResponse += "<p>" + stripLettersGetAutoComplete(firstName, false) + " " +
                      stripLettersGetAutoComplete(lastName, false) + "</p>";
      htmlResponse += "<h2>keep first and last letters algorithm:</h2>";
      htmlResponse += "<p>" + firstAndLast(firstName) + " " +
                      firstAndLast(lastName) + "</p>";
      htmlResponse += "<h2>double stripping algorithm:</h2>";
      htmlResponse += "<p>" + stripLettersGetAutoComplete(stripLettersGetAutoComplete(firstName,true),false) + " " +
                      stripLettersGetAutoComplete(stripLettersGetAutoComplete(lastName,true),false) + "</p>";
      htmlResponse += "</body></html>";

      return htmlResponse;
    }

    private String firstAndLast(String name) {
      char[] newPartial = new char[name.length()];

      newPartial[0] = name.charAt(0);
      newPartial[name.length() - 1] = name.charAt(name.length() - 1);
        
      for (int i = 1; i < (name.length() - 1); i++) {
        newPartial[i] = ' ';
      }
      List<String> matches = getTopNWords(new String(newPartial));
      return matches.get(0);
    }

    private String stripLettersGetAutoComplete(String name, boolean stripVowels) {
      HashSet<Character> vowels = new HashSet<Character>();
      vowels.add('A');
      vowels.add('E');
      vowels.add('I');
      vowels.add('O');
      vowels.add('U');
      vowels.add('Y');

      char[] newPartial = new char[name.length()];
      int i = 0;

      for (char letter: name.toCharArray()) {
        if (vowels.contains(letter)) {
          if (stripVowels) {
            newPartial[i] = ' ';
          } else {
            newPartial[i] = letter;
          }
        } else {
          if (stripVowels) {
            newPartial[i] = letter;
          } else {
            newPartial[i] = ' ';
          }
        }
        i++;
      }
      List<String> matches = getTopNWords(new String(newPartial));
      return matches.get(0);
    }

    private String makeStatsReport(String partialWord) {
      String htmlRespone = "<html><body>";
      htmlRespone += "<h2>Autocomplete Results</h2>";
      htmlRespone += "<p>" + listToString(getTopNWords(partialWord)) + "</p>";
      htmlRespone += "<h2>Next Letter Probability Results</h2>";
      htmlRespone += "<p>" + getLetters(partialWord) + "</p>";
      htmlRespone += "</body></html>";

      return htmlRespone;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
      response.setContentType("text/html;charset=UTF-8");
    } 

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<String> getTopNWords(java.lang.String arg0) {
        wordengine.statsservice.endpoint.StatsService port = 
                service.getStatsServicePort();
      return port.getTopNWords(arg0);
    }

    private String listToString(List<String> list) {
      String output = "";
      for (String word: list) {
        output += word + "<br>";
      }
      return output;
    }

    private String getLetters(java.lang.String arg0) {
        wordengine.statsservice.endpoint.StatsService port = 
                service.getStatsServicePort();
        List<Double> letterStats = port.getLetterStats(arg0);
      String output = "";
      char letter = 'A';
      for (Double prob: letterStats) {
        output += letter + ": " + prob  + "<br>";
        letter++;
      }
      return output;
    }
}
