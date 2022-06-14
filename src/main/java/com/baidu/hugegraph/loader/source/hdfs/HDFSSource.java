/*
 * Copyright 2017 HugeGraph Authors
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.baidu.hugegraph.loader.source.hdfs;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import com.baidu.hugegraph.loader.source.file.Compression;
import com.baidu.hugegraph.loader.source.file.DirFilter;
import com.baidu.hugegraph.loader.source.file.FileFilter;
import com.baidu.hugegraph.loader.source.file.FileFormat;
import com.baidu.hugegraph.loader.source.file.SkippedLine;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.baidu.hugegraph.loader.source.SourceType;
import com.baidu.hugegraph.loader.source.file.FileSource;
import com.baidu.hugegraph.util.E;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HDFSSource extends FileSource {

    @JsonProperty("core_site_path")
    private String coreSitePath;
    @JsonProperty("hdfs_site_path")
    private String hdfsSitePath;
    @JsonProperty("kerberos_config")
    private KerberosConfig kerberosConfig;

    @JsonCreator
    public HDFSSource(@JsonProperty("path") String path,
                      @JsonProperty("dir_filter") DirFilter dirFilter,
                      @JsonProperty("filter") FileFilter filter,
                      @JsonProperty("format") FileFormat format,
                      @JsonProperty("delimiter") String delimiter,
                      @JsonProperty("date_format") String dateFormat,
                      @JsonProperty("extra_date_formats")
                              List<String> extraDateFormats,
                      @JsonProperty("time_zone") String timeZone,
                      @JsonProperty("skipped_line") SkippedLine skippedLine,
                      @JsonProperty("compression") Compression compression,
                      @JsonProperty("batch_size") Integer batchSize) {
        super(path, dirFilter, filter, format, delimiter, dateFormat,
              extraDateFormats, timeZone, skippedLine, compression, batchSize);
    }

    @Override
    public SourceType type() {
        return SourceType.HDFS;
    }

    @Override
    public void check() throws IllegalArgumentException {
        super.check();
        E.checkArgument(!StringUtils.isEmpty(this.coreSitePath),
                        "The core_site_path can't be empty");
        File coreSiteFile = FileUtils.getFile(Paths.get(this.coreSitePath)
                                                   .toString());
        E.checkArgument(coreSiteFile.exists() && coreSiteFile.isFile(),
                        "The core site file '%s' is not an existing file",
                        coreSiteFile);

        if (this.hdfsSitePath != null) {
            File hdfsSiteFile = FileUtils.getFile(Paths.get(this.hdfsSitePath)
                                                       .toString());
            E.checkArgument(hdfsSiteFile.exists() && hdfsSiteFile.isFile(),
                            "The hdfs site file '%s' is not an existing file",
                            hdfsSiteFile);
        }

        if (this.kerberosConfig != null) {
            this.kerberosConfig.check();
        }
    }

    public String coreSitePath() {
        return this.coreSitePath;
    }

    public String hdfsSitePath() {
        return this.hdfsSitePath;
    }

    public KerberosConfig kerberosConfig() {
        return this.kerberosConfig;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.type(), this.path());
    }
}
