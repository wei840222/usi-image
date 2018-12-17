math.randomseed(os.time())

request = function()
    n = math.random(0, 65536)
    wrk.headers["Content-Type"] = "application/json"
    return wrk.format('GET', '/image-record/' .. n .. '_AOI.jpg', nil, nil)
end