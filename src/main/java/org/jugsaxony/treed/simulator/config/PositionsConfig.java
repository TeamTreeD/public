package org.jugsaxony.treed.simulator.config;

import org.jugsaxony.treed.root.FilePositions;
import org.jugsaxony.treed.root.StringPositions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Configuration
public class PositionsConfig
{
    @Value("${application.positionsFile:/treed_01.csv}")
    private String positionsFileName;

    @Bean
    public List<double[]> positions() throws IOException {
        URL url = getClass().getResource(positionsFileName);
        String str = Resources.toString(url, Charsets.UTF_8);

        List<double[]> result;
        StringPositions csvFile;

        csvFile = new StringPositions(str);
        result = csvFile.getAll();

        return result;
    }
}
