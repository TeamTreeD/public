import React, { useRef, useState } from 'react'
import { StrategyStatus } from './StrategyStatus';

class StatusInformationProps {
  strategyStatus: StrategyStatus;
}

export default function StatusInformation(props: StatusInformationProps) {
  if (props.strategyStatus == StrategyStatus.STRATEGY_RUNNING) {
    return (<></>)
  } else {
    return (
      <div>
        There is no strategy running.
      </div>
    )
  }
}