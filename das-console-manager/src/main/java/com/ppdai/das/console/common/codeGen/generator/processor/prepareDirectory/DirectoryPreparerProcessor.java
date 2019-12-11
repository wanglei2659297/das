package com.ppdai.das.console.common.codeGen.generator.processor.prepareDirectory;

import com.ppdai.das.console.common.codeGen.generator.java.context.DbSetGenContext;
import com.ppdai.das.console.common.codeGen.generator.java.context.JavaCodeGenContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;

@Slf4j
public class DirectoryPreparerProcessor {

    final public static String TABLE_FILE_NAME = "Table";
    final public static String ENTITY_FILE_NAME = "Entity";

    public static void process(DbSetGenContext ctx) throws Exception {
        File dir = new File(String.format("%s/%s/java", ctx.getGeneratePath(), ctx.getProjectId()));
        try {
            createFile("db", dir, ctx.isRegenerate());
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public static void process(JavaCodeGenContext ctx) throws Exception {
        File dir = new File(String.format("%s/%s/java", ctx.getGeneratePath(), ctx.getProjectId()));

        try {
            createFile(TABLE_FILE_NAME, dir, ctx.isRegenerate());
            createFile(ENTITY_FILE_NAME, dir, ctx.isRegenerate());
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    private static void createFile(String name, File dir, boolean regenerate) throws Exception {
        if (!dir.exists() && regenerate) {
            //FileUtils.forceDelete(dir);
        }
        File dbSetDir = new File(dir, name);
        if (!dbSetDir.exists()) {
            FileUtils.forceMkdir(dbSetDir);
        }
    }
}
