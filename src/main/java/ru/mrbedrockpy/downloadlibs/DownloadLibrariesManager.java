package ru.mrbedrockpy.downloadlibs;

import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DownloadLibrariesManager {

    public void getLibraries(String version) {

    }

    public List<Version> getVersions() {
        JSONObject versionManifest = NetUtil.getJSONObject("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json");
        JSONArray versionsJson = versionManifest.getJSONArray("versions");
        List<Version> versions = new ArrayList<>();
        versionsJson.forEach(version -> versions.add(parseFromJSON((JSONObject) version)));
        return versions;
    }

    public List<String> getVersionsStrings() {
        List<Version> versions = getVersions();
        List<String> versionsStrings = new ArrayList<>();
        versions.forEach(version -> versionsStrings.add(version.toString()));
        return versionsStrings;
    }

    private Version parseFromJSON(JSONObject versionJson) {
        return Version.builder()
                .id(versionJson.getString("id"))
                .type(versionJson.getString("type"))
                .url(versionJson.getString("url"))
                .releaseTime(versionJson.getString("releaseTime"))
                .build();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Version {
        public String id;
        public String type;
        public String url;
        public String releaseTime;

        @Override
        public String toString() {
            return String.format("%s %s", type, id);
        }
    }

}
