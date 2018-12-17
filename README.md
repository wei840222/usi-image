# usi-image

### API
** add a record **
```
POST /image-record

body
{
	"imageName": "2018112800039_AOI.jpg",
	"mergeFile": "20181128.merge",
	"startLine": 10007
}

result
201 Created
```

** get a record **
```
GET /image-record/{fileName}

result
{
	"imageName": "2018112800039_AOI.jpg",
	"mergeFile": "20181128.merge",
	"startLine": 10007
}
```

### Load Test
![](https://github.com/wei840222/usi-image/blob/master/loadTest/result.png?raw=true)