package com.baidu.hugegraph.loader.source.file;

import com.baidu.hugegraph.loader.constant.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirFilter {
    private static final String DEFAULT_INCLUDE;
    private static final String DEFAULT_EXCLUDE;

    static {
        DEFAULT_INCLUDE = "";
        DEFAULT_EXCLUDE = "";
    }

    @JsonProperty("include_regex")
    String includeRegex;
    @JsonProperty("exclude_regex")
    String excludeRegex;

    private transient Matcher includeMatcher;
    private transient Matcher excludeMatcher;

    public DirFilter() {
        this.includeRegex = DEFAULT_INCLUDE;
        this.excludeRegex = DEFAULT_EXCLUDE;
        this.includeMatcher = null;
        this.excludeMatcher = null;
    }

    private Matcher includeMatcher() {
        if (this.includeMatcher == null &&
            !StringUtils.isEmpty(this.includeRegex)) {
            this.includeMatcher = Pattern.compile(this.includeRegex)
                                         .matcher(Constants.EMPTY_STR);
        }
        return this.includeMatcher;
    }

    private Matcher excludeMatcher() {
        if (this.excludeMatcher == null &&
            !StringUtils.isEmpty(this.excludeRegex)) {
            this.excludeMatcher = Pattern.compile(this.excludeRegex)
                                         .matcher(Constants.EMPTY_STR);
        }

        return this.excludeMatcher;
    }

    private boolean includeMatch(String dirName) {
        if (!StringUtils.isEmpty(this.includeRegex)) {
            return this.includeMatcher().reset(dirName).matches();
        }

        return true;
    }

    private boolean excludeMatch(String dirName) {
        if (!StringUtils.isEmpty(this.excludeRegex)) {
            return this.excludeMatcher().reset(dirName).matches();
        }

        return false;
    }

    public boolean reserved(String dirName) {
        return this.includeMatch(dirName) && (!this.excludeMatch(dirName));
    }
}
