math.randomseed(os.time())

request = function()
    n = math.random(0, 65536)
    wrk.headers["Content-Type"] = "application/json"
    return wrk.format('POST', '/image-record', nil, "{\"imageName\":\"" .. n .. "_AOI.jpg\",\"mergeFile\":\"20181128.merge\",\"startLine\":10007}")
end