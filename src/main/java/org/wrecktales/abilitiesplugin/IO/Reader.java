package org.wrecktales.abilitiesplugin.IO;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Reader
{
    public HashMap<String, String> data;
    public FileWriter outputWriter;
    private final Yaml yaml;
    private String inputPath, outputPath;

    public static Reader create(String inputPath, String outputPath)
    {
        File file = new File(inputPath);
        if (!file.exists())
        {
            try
            {
                if (!file.createNewFile())
                {
                    throw new RuntimeException("Could not create data file.");
                }
            }
            catch (IOException e) { throw new RuntimeException(e); }
        }

        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(inputPath))
        {
            Reader reader = new Reader(yaml.load(inputStream), yaml, new FileWriter(outputPath, true));
            reader.outputPath = outputPath;
            reader.inputPath = inputPath;

            return reader;
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public Reader(HashMap<String, String> data, Yaml yaml, FileWriter outputWriter)
    {
        this.data = data;
        if (data == null)
        {
            this.data = new HashMap<>();
        }

        this.yaml = yaml;
        this.outputWriter = outputWriter;
    }

    public void dump(String str, String obj)
    {
        if (data.containsKey(str))
        {
            replace(str, obj);
            return;
        }

        data.put(str, obj);
        yaml.dump(new HashMap<>()
        {{ put(str, obj); }}, outputWriter);
    }

    private void replace(String str, String obj)
    {
        List<String> newLines = new ArrayList<>();
        data.replace(str, obj);

        for (Map.Entry<String, String> map : data.entrySet())
        {
            if (!Objects.equals(map.getKey(), str))
            {
                newLines.add(map.getKey() + ": " + map.getValue());
                continue;
            }

            newLines.add(str + ": " + obj);
        }

        try { Files.write(Path.of(outputPath), newLines, StandardCharsets.UTF_8); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
