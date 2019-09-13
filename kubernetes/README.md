# Java vertical scaling test

## Nodes

This addon assumes that there are 6 nodes in the cluster. Nodes are [labeled](https://github.com/bianchi2/kube-deployer/blob/master/scripts/labelnodes.sh) in a special way so that Kubernetes schedules 1 Java pod per node.
Currently, the addon does not check if there are enough nodes, so if nodes count is under 6, one of the pods won't be scheduled.

Also, Jelastic nodes displayNames are modified to make statistics analysis simpler.


## Deployments

This addon creates 6 K8s deployments in `java` namespace. Deployments are defined in .yaml files and can be edited if necessary.

The addon processes retrieved yamls and replaces placeholders with desired values (at this moment only memory limits and JAVA_OPTS).
You can make more things configurable is necessary.

```
curl ${baseUrl}/deployments/adoptopenjdk.yaml | sed "s/%JAVA_OPTS%/${globals.common} ${globals.adoptopenjdk}/g" | sed "s/%MEM_LIMIT%/${settings.adoptopenjdkLimit}/g" | kubectl apply -f -
```

## Memory Limits

The default memory limit is `3Gi`. You can change it for each pod in `${settings.jdkFlavor}`:

```
- type: string
  name: zulujdkLimit
  caption: Zulu Pod Mem Limit
  default: 3Gi
  inputType: hidden
```
Remove `inputType: hidden` if you want to be able to define memory requests for containers on UI.

## JAVA_OPTS

There are common `JAVA_OPTS` defined in `{globals.common}` and `JAVA_OPTS` defined for each individual pod - `$globals.jdkFlavor`

```
globals:
  common: -Xmx3g -Xms32m -XX:+UseCompressedOops
  zing: -XX:-AutoTuneResourceDefaultsBasedOnXmx
  openjdk: -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -XX:ZUncommitDelay=1 -XX:ZCollectionInterval=30
  openj9: -XX:+IdleTuningCompactOnIdle -XX:+IdleTuningGcOnIdle -XX:IdleTuningMinIdleWaitTime=1 -Xjit:waitTimeToEnterDeepIdleMode=1000
  adoptopenjdk: -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGCHeuristics=compact
  liberica: -XX:+UseG1GC -XX:G1PeriodicGCInterval=10k
  zulujdk: -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
```

## Redeploy

When this addon is repeatedly applied to a Kubernetes cluster, all existing deployments in `java` namespace are forcefully deleted,
thus, there is no need to manually clean up resources before a repeated deployment.

## Monitoring

The addon assumes that Grafana is installed in a cluster and is available at `${env.url}/grafana`.
Several dashboards are imported to track Java pods memory utilization.

Once installed, the addon will print URL and names of dashboards to check out.
