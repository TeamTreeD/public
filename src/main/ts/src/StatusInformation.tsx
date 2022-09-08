import React, { useRef, useState } from 'react'
import { Card } from 'react-bootstrap';
import ReloadHint from './ReloadHint';
import { StrategyStatus } from './StrategyStatus';

class StatusInformationProps {
  strategyStatus: StrategyStatus;
}

export default function StatusInformation(props: StatusInformationProps) {
  if (props.strategyStatus == StrategyStatus.INCOMPLETE_STRATEGY) {
    return (
      <Card className="defaultCard" body>
        Your strategy is not complete yet. Please re-run the tests in the
        Entwicklerheld portal and follow the hints shown there.
        <ReloadHint/>
      </Card>
    )
  }
  if (props.strategyStatus == StrategyStatus.STRATEGY_RUNNING) {
    return (<></>)
  } else {
    return (
      <Card className="defaultCard" body>
        There is no strategy running. A common reason for this is that
        you didn't run the tests in the Entwicklerheld portal yet. It may
        even be a good idea to run the tests there, if you've already
        run them.
        <ReloadHint/>
      </Card>
    )
  }
}

