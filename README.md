# ReadWriteConcurrency
#### LockFree R/W that translates to XADD instruction <br/>
```readers = 1 writers = 1 LockFreeVehicle =>   reads=45,688,066 : [45,688,066]  moves= 20,886,244 : [20,886,244]  readAttempts  = [45,688,066]  writeAttempts = [20,886,244]  moveHappened  = [12,141,479]  ```<br/>
```readers = 1 writers = 1 LockFreeVehicle =>   reads=18,225,095 : [18,225,095]  moves= 21,969,852 : [21,969,852]  readAttempts  = [18,225,095]  writeAttempts = [21,969,852]  moveHappened  = [14,430,792]```  <br />
```readers = 1 writers = 1 LockFreeVehicle =>   reads=22,336,630 : [22,336,630]  moves= 21,938,689 : [21,938,689]  readAttempts  = [22,336,630]  writeAttempts = [21,938,689]  moveHappened  = [16,558,145]```  <br />
```readers = 1 writers = 1 LockFreeVehicle =>   reads=24,153,575 : [24,153,575]  moves= 22,181,366 : [22,181,366]  readAttempts  = [24,153,575]  writeAttempts = [22,181,366]  moveHappened  = [16,264,505]```  <br />
```readers = 1 writers = 1 LockFreeVehicle =>   reads=25,434,460 : [25,434,460]  moves= 21,720,478 : [21,720,478]  readAttempts  = [25,434,460]  writeAttempts = [21,720,478]  moveHappened  = [15,514,884]```  <br />

#### Synchronized R/W 
```readers = 1 writers = 1 SynchronizedVehicle =>   reads=13,726,135 : [13,726,135]  moves= 14,829,529 : [14,829,529]  readAttempts  = [13,726,135]  writeAttempts = [14,829,529]  moveHappened  = [340,110]``` <br/> 
```readers = 1 writers = 1 SynchronizedVehicle =>   reads=10,941,694 : [10,941,694]  moves= 11,985,436 : [11,985,436]  readAttempts  = [10,941,694]  writeAttempts = [11,985,436]  moveHappened  = [68,403]``` <br/> 
```readers = 1 writers = 1 SynchronizedVehicle =>   reads=9,375,561 : [9,375,561]  moves= 9,606,868 : [9,606,868]  readAttempts  = [9,375,561]  writeAttempts = [9,606,868]  moveHappened  = [33,738]``` <br/> 
```readers = 1 writers = 1 SynchronizedVehicle =>   reads=10,066,001 : [10,066,001]  moves= 10,002,629 : [10,002,629]  readAttempts  = [10,066,001]  writeAttempts = [10,002,629]  moveHappened  = [38,434]``` <br/> 
```readers = 1 writers = 1 SynchronizedVehicle =>   reads=11,239,181 : [11,239,181]  moves= 11,635,856 : [11,635,856]  readAttempts  = [11,239,181]  writeAttempts = [11,635,856]  moveHappened  = [1,018,590]``` <br/> 

#### StampedLock R/W 
```readers = 1 writers = 1 StampedVehicle =>   reads=5,201,483 : [5,201,483]  moves= 13,579,499 : [13,579,499]  readAttempts  = [16,939,932]  writeAttempts = [13,579,499]  moveHappened  = [2,561,435] ``` <br/> 
```readers = 1 writers = 1 StampedVehicle =>   reads=4,571,845 : [4,571,845]  moves= 12,206,629 : [12,206,629]  readAttempts  = [15,832,158]  writeAttempts = [12,206,629]  moveHappened  = [2,368,989] ``` <br/> 
```readers = 1 writers = 1 StampedVehicle =>   reads=19,738,678 : [19,738,678]  moves= 11,286,049 : [11,286,049]  readAttempts  = [46,989,299]  writeAttempts = [11,286,049]  moveHappened  = [5,145,162] ``` <br/> 
```readers = 1 writers = 1 StampedVehicle =>   reads=20,724,521 : [20,724,521]  moves= 11,526,209 : [11,526,209]  readAttempts  = [49,214,541]  writeAttempts = [11,526,209]  moveHappened  = [5,442,010] ``` <br/> 
```readers = 1 writers = 1 StampedVehicle =>   reads=20,371,167 : [20,371,167]  moves= 11,480,306 : [11,480,306]  readAttempts  = [48,430,905]  writeAttempts = [11,480,306]  moveHappened  = [5,390,876] ``` <br/> 
