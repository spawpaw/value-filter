package com.spawpaw.desensitization.extras.executors;

import com.spawpaw.desensitization.core.annotations.SensitiveString;
import com.spawpaw.desensitization.core.executor.AbstractStringDesensitizationExecutor;
import com.spawpaw.desensitization.extras.string.SensitiveChineseName;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class SensitiveChineseNameDesensitizationExecutor extends AbstractStringDesensitizationExecutor {
    /**
     * 复姓
     */
    private HashSet<String> compoundSurname = new HashSet<>(Arrays.asList(
            "欧阳", "太史", "端木", "上官", "司马", "东方", "独孤", "南宫", "万俟", "闻人", "夏侯", "诸葛", "尉迟",
            "公羊", "赫连", "澹台", "皇甫", "宗政", "濮阳", "公冶", "太叔", "申屠", "公孙", "慕容", "仲孙", "钟离",
            "长孙", "宇文", "司徒", "鲜于", "司空", "闾丘", "子车", "亓官", "司寇", "巫马", "公西", "颛孙", "壤驷",
            "公良", "漆雕", "乐正", "宰父", "谷梁", "拓跋", "夹谷", "轩辕", "令狐", "段干", "百里", "呼延", "东郭",
            "南门", "羊舌", "微生", "公户", "公玉", "公仪", "梁丘", "公仲", "公上", "公门", "公山", "公坚", "左丘",
            "公伯", "西门", "公祖", "第五", "公乘", "贯丘", "公皙", "南荣", "东里", "东宫", "仲长", "子书", "子桑",
            "即墨", "达奚", "褚师", "吴铭"
    ));

    @Override
    public String desensitize(Object object, Field field, Map<Class<? extends Annotation>, ? extends Annotation> annotationMap, String value) {
        SensitiveString metaAnnotation = (SensitiveString) annotationMap.get(SensitiveString.class);
        if (value.length() <= 1) {//单字，不脱敏（本来就不会这样起名字，脱不脱敏无所谓）
            return value;
        } else if (value.length() == 2) {//双字，隐藏后一个字
            return value.substring(0, 1) + metaAnnotation.replacement();
        } else if (value.length() == 3) {//三个字
            if (compoundSurname.contains(value.substring(0, 2)))//如果是复姓，隐藏最后一个字
                return value.substring(0, 2) + metaAnnotation.replacement();
            else {//如果不是复姓，隐藏中间的字
                return value.substring(0, 1) + metaAnnotation.replacement() + value.charAt(value.length() - 1);
            }
        } else {//大于等于四个字
            if (compoundSurname.contains(value.substring(0, 2))) {//如果是复姓
                return value.substring(0, 2) + metaAnnotation.replacement() + value.charAt(value.length() - 1);
            } else {
                return value.substring(0, 2) + metaAnnotation.replacement() + value.charAt(value.length() - 1);
            }
        }
    }

    @Override
    public List<Class<? extends Annotation>> necessaryAnnotations() {
        List<Class<? extends Annotation>> list = new LinkedList<>();
        list.add(SensitiveChineseName.class);
        list.add(SensitiveString.class);
        return list;
    }
}