package com.weichai.modules.pads.parsedoc.utils;

import org.apache.commons.io.IOUtils;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 对doc文件进行合并
 */
public class MergeDocUtils {

    private static boolean flag = false;

    /**
     * 文件合并
     *
     * @param list 需要合并的文件
     * @param path 合并之后文件的存放路径
     */
    public static void mergeDocx(List<String> list, String path) throws Docx4JException, IOException {
        WordprocessingMLPackage target = mergeDocx(list);
        //保存合并之后的文件
        target.save(new File(path));
        flag = false;
    }

    /**
     * 对文件进行合并
     *
     * @param strs
     * @return
     * @throws Docx4JException
     * @throws IOException
     */
    public static WordprocessingMLPackage mergeDocx(List<String> strs) throws Docx4JException, IOException {

        WordprocessingMLPackage target = null;
        WordprocessingMLPackage newTarget;
        int chunkId = 0;
        Iterator<String> it = strs.iterator();
        while (it.hasNext()) {
            String is = it.next();
            FileInputStream in = new FileInputStream(is);
            if (is != null) {
                //加载第一个文件，依次追加到该文件的后面
                if (target == null) {
                    target = WordprocessingMLPackage.load(in);
                    newTarget = target;
                } else {
                    newTarget = WordprocessingMLPackage.load(in);
                    //往文件后面追加内容
                    insertDocx(target.getMainDocumentPart(), IOUtils.toByteArray(new FileInputStream(is)), chunkId++);
                }
                //设置纸张的方向和大小
                /*if (!flag) {
                    setPgSz(newTarget, target);
                }*/
            }
        }
        return target;
    }

    /**
     * 设置纸张的大小和方向
     *
     * @param newTarget
     * @param target
     * @throws Docx4JException
     */
    private static void setPgSz(WordprocessingMLPackage newTarget, WordprocessingMLPackage target) throws Docx4JException {
        MainDocumentPart TaMain = target.getMainDocumentPart();
        //Document对象表示文档本身。
        Document taDoc = TaMain.getContents();
        //Document中的Body对象表示前景内容，还有Background对象表示背景内容。
        Body taBody = taDoc.getBody();
        SectPr taSectPr = taBody.getSectPr();
        SectPr.PgSz taPgSz = taSectPr.getPgSz();
        SectPr.PgMar taPgMar = taSectPr.getPgMar();

        MainDocumentPart main = newTarget.getMainDocumentPart();
        //Document对象表示文档本身。
        Document doc = main.getContents();
        //Document中的Body对象表示前景内容，还有Background对象表示背景内容。
        Body body = doc.getBody();
        SectPr sectPr = body.getSectPr();
        SectPr.PgSz pgSz = sectPr.getPgSz();
        STPageOrientation orient = pgSz.getOrient();
        SectPr.PgMar pgMar = sectPr.getPgMar();
        //判断是否为横向纸张
        if (orient != null && orient.equals(STPageOrientation.LANDSCAPE)) {
            flag = true;
            if (newTarget == target) {
                return;
            }
            //获取横向纸张的高度和宽度
            System.out.println("调整纸张的方向");
            BigInteger w = pgSz.getW();
            BigInteger h = pgSz.getH();
            taPgSz.setW(w);
            taPgSz.setH(h);
            //设置纸张为横向
            taPgSz.setOrient(STPageOrientation.LANDSCAPE);
            BigInteger top = pgMar.getTop();
            BigInteger right = pgMar.getRight();
            BigInteger bottom = pgMar.getBottom();
            BigInteger left = pgMar.getLeft();
            taPgMar.setTop(top);
            taPgMar.setRight(right);
            taPgMar.setBottom(bottom);
            taPgMar.setLeft(left);
        }
    }

    /**
     * 向文件后面追加内容
     *
     * @param main
     * @param bytes
     * @param chunkId
     */
    private static void insertDocx(MainDocumentPart main, byte[] bytes, int chunkId) throws InvalidFormatException {
        AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(
                new PartName("/part" + chunkId + ".docx"));
        afiPart.setBinaryData(bytes);
        Relationship altChunkRel = main.addTargetPart(afiPart);
        CTAltChunk chunk = Context.getWmlObjectFactory().createCTAltChunk();
        chunk.setId(altChunkRel.getId());
        main.addObject(chunk);
    }

    public static void main(String[] args) throws Docx4JException, IOException {
        MergeDocUtils wordUtil = new MergeDocUtils();
        String template = "E:\\doc";
        List<String> list = new ArrayList<String>();
        list.add(template + "\\结构协议.docx");
        list.add(template + "\\电控协议部分.docx");
        wordUtil.mergeDocx(list, template + "\\2000.docx");
    }

}
