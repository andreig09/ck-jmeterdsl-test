package us.abstracta.jmeter.javadsl.sample;

//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

import java.io.IOException;
//import java.time.Duration;
import org.junit.jupiter.api.Test;
//import us.abstracta.jmeter.javadsl.core.TestPlanStats;

public class PerformanceTest {

  @Test
  public void testPerformance() throws IOException {
    testPlan(
        threadGroup(1, 1,
            httpHeaders()
              .header("pragma", "no-cache")
              .header("cache-control", "no-cache")
              .header("sec-ch-ua", "\"Chromium\";v=\"116\", \"Not)A;Brand\";v=\"24\", \"Google Chrome\";v=\"116\"")
              .header("sec-ch-ua-mobile", "?0")
              .header("sec-ch-ua-platform", "\"macOS\"")
              .header("upgrade-insecure-requests", "1")
              .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36")
              .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
              .header("sec-fetch-site", "cross-site")
              .header("sec-fetch-mode", "navigate")
              .header("sec-fetch-user", "?1")
              .header("sec-fetch-dest", "document")
              .header("accept-encoding", "gzip, deflate, br")
              .header("accept-language", "es,en;q=0.9,en-US;q=0.8")
              .header("cookie", "limit=600"),
            httpSampler("https://<url>")
            .children(
              responseAssertion(null).containsSubstrings("Search Results"),
              regexExtractor(<regex>).template("$1$").matchNumber(-1),
              jsr223PostProcessor("int numIteraciones = Integer.parseInt(vars.get(\"precio_matchNr\"));\n"
                + "double suma = 0.0;\n"
                + "double valorMaximo = Double.NEGATIVE_INFINITY;\n"
                + "List<String> variablesMaximas = new ArrayList<String>();\n"
                + "for (int i = 1; i <= numIteraciones; i++) {\n"
                + "    String variableName = \"precio_\" + i + \"_g2\";\n"
                + "    String valorStr = vars.get(variableName);\n"
                + "    if (valorStr != null) {\n"
                + "        double valor = Double.parseDouble(valorStr);\n"
                + "        suma += valor;\n"
                + "        if (valor > valorMaximo) {\n"
                + "            valorMaximo = valor;\n"
                + "            variablesMaximas.clear(); // Borra las variables anteriores\n"
                + "            variablesMaximas.add(\"precio_\" + i);\n"
                + "        } else if (valor == valorMaximo) {\n"
                + "            variablesMaximas.add(\"precio_\" + i);\n}\n"
                + "    }\n"
                + "}\n"
                + "log.info(\"La cantidad de cartas es: \" + numIteraciones)\n"
                + "log.info(\"el valor total de las cartas es: \" + suma + \" usd\")\n"
                + "for (String variable : variablesMaximas) {\n"
                + "    String variableG1 = vars.get(variable + \"_g1\");\n"
                + "    String variableG2 = vars.get(variable + \"_g2\");\n"
                + "    log.info(\"La carta más cara es \" + variableG1 + \" y su valor es \" + variableG2 + \" usd\")\n"
                + "}\n")
              //debugPostProcessor()
            )
        )
        //resultsTreeVisualizer()
    //).run();
    ).showInGui();
  }

}
