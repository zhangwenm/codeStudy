## mysql定义tinnyint（1）
> Q：当字段类型定义为tinnyint（1）时，用map或者ReultSet接收时会返回为boolean类型
> A： 1：在jdbcurl添加参数：tinyint1isbit=false（默认为true）2：字段改为tinnyint（4）占用空间相同






