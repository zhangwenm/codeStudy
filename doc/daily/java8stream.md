## 流式处理
- map
  - Map<String, String>  scoreMap = Lists.newArrayList(yearBillInfoVO.getScores().split(",")).stream().filter(fl->StringUtils.isNotBlank(fl) &&
    fl.split(":").length == 2).collect(HashMap::new,(k, v) -> k.put(v.split(":")[0], v.split(":")[1]), HashMap::putAll);







