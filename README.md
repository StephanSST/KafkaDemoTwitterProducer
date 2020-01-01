# KafkaDemoTwitterProducer

### Apply Kafka Topic with Strimzi
##### OpenShift command
```bash
oc login https://console.baloise.dev --token=.........
oc create -f FILENAME [flags]
```

##### File content
```yaml
apiVersion: kafka.strimzi.io/v1beta1
kind: KafkaTopic
metadata:
  name: twitter
  labels:
    strimzi.io/cluster: ssteps
spec:
  partitions: 3
  replicas: 3
```
  